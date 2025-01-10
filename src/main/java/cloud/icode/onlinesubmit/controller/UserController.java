package cloud.icode.onlinesubmit.controller;

import cloud.icode.onlinesubmit.annoation.Log;
import cloud.icode.onlinesubmit.common.ResponseResult;
import cloud.icode.onlinesubmit.enums.AppHttpCodeEnum;
import cloud.icode.onlinesubmit.model.dto.UserLoginRequest;
import cloud.icode.onlinesubmit.model.dto.UserRegisterRequest;
import cloud.icode.onlinesubmit.model.vo.UserVo;
import cloud.icode.onlinesubmit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/5
 * 文件描述: UserController
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080"}, allowCredentials = "true")
@Api(tags = "用户模块")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    @Log(name = "用户登录模块")
    public ResponseResult login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        //封装数据
        UserVo userVo = userService.userLogin(userLoginRequest, request);
        if (userVo == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        return ResponseResult.okResult(userVo);
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    @Log(name = "用户注册模块")
    public ResponseResult register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        //参数校验
        if (userRegisterRequest == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        int count = userService.registerUser(userRegisterRequest);
        if (count == -1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

}
