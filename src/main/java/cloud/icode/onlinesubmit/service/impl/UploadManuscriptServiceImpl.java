package cloud.icode.onlinesubmit.service.impl;

import cloud.icode.onlinesubmit.common.CommonConstant;
import cloud.icode.onlinesubmit.common.ResponseResult;
import cloud.icode.onlinesubmit.dao.ManuscriptMapper;
import cloud.icode.onlinesubmit.enums.AppHttpCodeEnum;
import cloud.icode.onlinesubmit.enums.ManuscriptEnum;
import cloud.icode.onlinesubmit.exception.CustomException;
import cloud.icode.onlinesubmit.model.Manuscript;
import cloud.icode.onlinesubmit.model.ManuscriptExample;
import cloud.icode.onlinesubmit.model.dto.UploadManuscriptRequest;
import cloud.icode.onlinesubmit.model.vo.ManuscriptVo;
import cloud.icode.onlinesubmit.model.vo.UserVo;
import cloud.icode.onlinesubmit.service.UploadManuscriptService;
import cloud.icode.onlinesubmit.service.UserService;
import cloud.icode.onlinesubmit.utils.FileUtil;
import cloud.icode.onlinesubmit.utils.ToolUtils;
import cloud.icode.onlinesubmit.utils.UserContext;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/17 下午1:11
 * 文件描述: UploadManuscriptServiceImpl
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UploadManuscriptServiceImpl implements UploadManuscriptService {

    @Value("${file.upload.max}")
    private String uploadManuscriptMaxSize;
    @Value("${file.upload.min}")
    private String uploadManuscriptMinSize;
    @Value("${file.manuscript.suffix}")
    private String fileManuscriptSuffix;
    @Value("${file.img.suffix}")
    private String fileImgSuffix;
    @Value("${file.upload.path}")
    private String fileUploadPath;

    private final ManuscriptMapper manuscriptMapper;
    private final UserService userService;

    @Override
    public boolean uploadManuscript(MultipartFile[] file, UploadManuscriptRequest request, HttpServletRequest httpServletRequest) {
        //参数校验
        if (file == null || file.length < 2) {
            throw new CustomException(AppHttpCodeEnum.FILE_UPLOAD_AT_LEAST_TWO);
        }
        //校验文件大小
        MultipartFile imgFile = file[0];
        long imgFileSize = imgFile.getSize();
        long maxSize = Long.parseLong(uploadManuscriptMaxSize);
        long minSize = Long.parseLong(uploadManuscriptMinSize);
        if (imgFileSize > maxSize || imgFileSize < minSize) {
            throw new CustomException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }
        //校验文件格式
        String imgFileName = imgFile.getOriginalFilename();
        if (StrUtil.isBlank(imgFileName)) {
            throw new CustomException(AppHttpCodeEnum.FILE_SUFFIX_ERROR);
        }

        //将文件转换为Base64存储在数据库中
        Manuscript manuscript = BeanUtil.copyProperties(request, Manuscript.class);
        String imgSuffix = StrUtil.subSuf(imgFileName, imgFileName.indexOf(".") + 1);
        //文档文件
        MultipartFile multipartFile = null;
        try {
            if (fileImgSuffix.contains(imgSuffix)) {
                multipartFile = file[1];
                manuscript.setCoverImg(Base64.encode(file[0].getBytes()));
            } else {
                multipartFile = file[0];
                manuscript.setCoverImg(Base64.encode(file[1].getBytes()));
            }
        } catch (Exception e) {
            log.info("error is {}", e.getMessage());
        }

        //获取文档名称
        String filename = null;
        if (multipartFile != null) {
            filename = IdUtil.simpleUUID() + multipartFile.getOriginalFilename();
        }
        if (StrUtil.isBlank(filename)) {
            throw new CustomException(AppHttpCodeEnum.FILE_NAME_IS_NOT_EMPTY);
        }
        //文件夹不存在就创建
        File saveFile = new File(fileUploadPath + ToolUtils.getYYYYMMddString());
        if (!saveFile.exists()) {
            saveFile.mkdir();
        }

        //设置文件路径
        String filePath = fileUploadPath + ToolUtils.getYYYYMMddString() + "/" + filename;
        //保存文件
        try {
            multipartFile.transferTo(new File(filePath));
        } catch (Exception e) {
            log.error("error is {}", e.getMessage());
        }

        //组装剩余数据
        //获取稿件上传时间
        String date = DateUtil.formatDate(request.getDate());
        String time = DateUtil.formatTime(request.getTime());
        DateTime dateTime = DateUtil.parse(date + " " + time);
        manuscript.setSubmitTime(dateTime);
        manuscript.setFileName(ToolUtils.getYYYYMMddString() + "/" + filename);
        //将当前用户ID存储到userId
        if (UserContext.getUser() != null) {
            Long userId = UserContext.getUser();
            manuscript.setUserId(userId);
        }

        //插入数据库
        int result = manuscriptMapper.insertSelective(manuscript);
        //插入失败
        if (result != 1) {
            log.info("插入数据失败：{}", manuscript);
            throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
        }

        return true;
    }

    /**
     * 查询稿件列表
     *
     * @return
     */
    @Override
    public ResponseResult listManuscripts() {
        //获取当前用户信息
        Long user = UserContext.getUser();
        if (user == null) {
            throw new CustomException(AppHttpCodeEnum.NEED_LOGIN);
        }

        //查询稿件信息
        ManuscriptExample example = new ManuscriptExample();
        example.setOrderByClause("id desc limit 0,10");
        example.createCriteria().andIsDeleteEqualTo(CommonConstant.NO_DELETE)
                .andUserIdEqualTo(UserContext.getUser());
        List<Manuscript> manuscriptList = manuscriptMapper.selectByExampleWithBLOBs(example);
        if (CollUtil.isEmpty(manuscriptList)) {
            return ResponseResult.okResult(new ArrayList<Manuscript>());
        } else {
            List<ManuscriptVo> manuscriptVoList = new ArrayList<>();
            manuscriptList.forEach(manuscript -> {
                ManuscriptVo manuscriptVo = BeanUtil.copyProperties(manuscript, ManuscriptVo.class);
                //获取用户信息
                UserVo userInfo = userService.getUserInfo(UserContext.getUser());
                //给nickname赋值
                manuscriptVo.setNickName(userInfo.getNickname());
                manuscriptVoList.add(manuscriptVo);
                //赋值审核状态
                ManuscriptEnum manuscriptEnum = ManuscriptEnum.getUserEnum(manuscript.getStatus());
                if (manuscriptEnum != null) {
                    manuscriptVo.setStatus(manuscriptEnum.getName());
                }
            });
            return ResponseResult.okResult(manuscriptVoList);
        }
    }

    /**
     * 稿件下载
     *
     * @param filename
     * @param httpServletResponse
     */
    @Override
    public void downloadManuscript(HttpServletRequest request, HttpServletResponse httpServletResponse, String filename) {
        if (StrUtil.isBlank(filename)) {
            throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
        }

        //下载文件
        File file = new File(fileUploadPath + filename);
        if(!file.exists()){
            throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
        }
        boolean result = FileUtil.downloadFile(httpServletResponse, file);
        if (!result) {
            log.info("下载文档数据：{}失败！！！", filename);
        }else {
            log.info("下载文档数据：{}成功！！！", filename);
        }

    }

    public static void main(String[] args) {
        String encode = Base64.encode("123456".getBytes());
        System.out.println(encode);
        String s = Base64.decodeStr(encode);
        System.out.println(s);

        Date date = new Date();
        System.out.println(date);

    }
}
