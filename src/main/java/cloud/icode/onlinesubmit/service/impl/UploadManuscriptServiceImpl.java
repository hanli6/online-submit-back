package cloud.icode.onlinesubmit.service.impl;

import cloud.icode.onlinesubmit.dao.ManuscriptMapper;
import cloud.icode.onlinesubmit.enums.AppHttpCodeEnum;
import cloud.icode.onlinesubmit.exception.CustomException;
import cloud.icode.onlinesubmit.model.ManuscriptWithBLOBs;
import cloud.icode.onlinesubmit.model.dto.UploadManuscriptRequest;
import cloud.icode.onlinesubmit.service.UploadManuscriptService;
import cloud.icode.onlinesubmit.utils.UserContext;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

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

    private final ManuscriptMapper manuscriptMapper;

    @Override
    public boolean uploadManuscript(MultipartFile[] file, UploadManuscriptRequest request, HttpServletRequest httpServletRequest) {
        //参数校验
        if (file == null || file.length < 2) {
            throw new CustomException(AppHttpCodeEnum.FILE_UPLOAD_AT_LEAST_TWO);
        }
        //校验文件大小
        MultipartFile imgFile = file[0];
        MultipartFile manuscriptFile = file[1];
        long imgFileSize = imgFile.getSize();
        long manuscriptFileSize = manuscriptFile.getSize();
        long maxSize = Long.parseLong(uploadManuscriptMaxSize);
        long minSize = Long.parseLong(uploadManuscriptMinSize);
        if (imgFileSize > maxSize || imgFileSize < minSize) {
            throw new CustomException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }
        if (manuscriptFileSize > maxSize || manuscriptFileSize < minSize) {
            throw new CustomException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }
        //校验文件格式
        String imgFileName = imgFile.getOriginalFilename();
        String manuscriptFileName = manuscriptFile.getOriginalFilename();
        String imgSuffix = imgFileName.substring(imgFileName.indexOf(".") + 1);
        String manuscriptSuffix = manuscriptFileName.substring(manuscriptFileName.indexOf(".") + 1);
        if (!(fileImgSuffix.contains(imgSuffix) && fileManuscriptSuffix.contains(manuscriptSuffix))) {
            throw new CustomException(AppHttpCodeEnum.FILE_SUFFIX_ERROR);
        }

        //将文件转换为Base64存储在数据库中
        String imgStr = "";
        String manuscriptStr = "";
        try {
            imgStr = Base64.encode(imgFile.getBytes());
            manuscriptStr = Base64.encode(manuscriptFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //组装数据
        //剩余数据自动填充
        ManuscriptWithBLOBs manuscriptWithBLOBs = BeanUtil.copyProperties(request, ManuscriptWithBLOBs.class);
        manuscriptWithBLOBs.setCoverImg(imgStr);
        manuscriptWithBLOBs.setManuscriptFile(manuscriptStr);
        //获取稿件上传时间
        String date = DateUtil.formatDate(request.getDate());
        String time = DateUtil.formatTime(request.getTime());
        DateTime dateTime = DateUtil.parse(date + " " + time);
        manuscriptWithBLOBs.setSubmitTime(dateTime);
        //将当前用户ID存储到userId
        if (UserContext.getUser() != null) {
            Long userId = UserContext.getUser();
            manuscriptWithBLOBs.setUserId(userId);
        }

        //插入数据库
        int result = manuscriptMapper.insertSelective(manuscriptWithBLOBs);
        //插入失败
        if (result != 1) {
            log.info("插入数据失败：{}", manuscriptWithBLOBs);
            throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
        }

        return true;
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
