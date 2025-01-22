package cloud.icode.onlinesubmit.service;

import cloud.icode.onlinesubmit.model.dto.UploadManuscriptRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/10 下午9:43
 * 文件描述: 稿件上传
 */
public interface UploadManuscriptService {

    /**
     * 稿件上传
     * @param file
     * @param request
     * @return
     */
    public boolean uploadManuscript(MultipartFile[] file, UploadManuscriptRequest request, HttpServletRequest httpServletRequest);
}
