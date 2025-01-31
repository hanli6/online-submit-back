package cloud.icode.onlinesubmit.controller;

import cloud.icode.onlinesubmit.annoation.Log;
import cloud.icode.onlinesubmit.common.ResponseResult;
import cloud.icode.onlinesubmit.model.dto.UploadManuscriptRequest;
import cloud.icode.onlinesubmit.service.UploadManuscriptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/5
 * 文件描述: 稿件模块
 */
@RestController
@RequestMapping("/manuscript")
@RequiredArgsConstructor
@Api(tags = "稿件模块")
public class UploadManuscriptController {

    private final UploadManuscriptService uploadManuscriptService;

    @ApiOperation(value = "稿件上传接口")
    @Log(name = "稿件上传模块")
    @PostMapping("/upload")
    public ResponseResult uploadManuscript(@RequestParam(value = "files", required = true) MultipartFile[] files, @Valid UploadManuscriptRequest request, HttpServletRequest httpServletRequest) {
        return ResponseResult.okResult(uploadManuscriptService.uploadManuscript(files, request,httpServletRequest));
    }

    @ApiOperation(value = "稿件查询接口")
    @Log(name = "稿件查询模块")
    @GetMapping("/list")
    public ResponseResult listManuscript() {
        return uploadManuscriptService.listManuscripts();
    }

    @ApiOperation(value = "稿件下载接口")
    @Log(name = "稿件下载模块")
    @GetMapping("/download")
    public void downloadManuscript(HttpServletRequest request, HttpServletResponse response,@RequestParam("filename") String filename) {
        uploadManuscriptService.downloadManuscript(request,response,filename);
    }
}
