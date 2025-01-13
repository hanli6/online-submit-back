package cloud.icode.onlinesubmit.controller;

import cloud.icode.onlinesubmit.common.ResponseResult;
import cloud.icode.onlinesubmit.model.vo.OperateLogVo;
import cloud.icode.onlinesubmit.service.OperateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/5
 * 文件描述: 日志模块
 */
@RestController
@RequestMapping("/operate_log")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080"}, allowCredentials = "true")
@Api(tags = "日志模块")
public class OperateLogController {
    private final OperateLogService operateLogService;

    @ApiOperation(value = "日志列表接口")
    @GetMapping("/list")
    public ResponseResult getOperateLog() {
        //封装数据
        List<OperateLogVo> result = operateLogService.operateLogList();

        return ResponseResult.okResult(result);
    }

}
