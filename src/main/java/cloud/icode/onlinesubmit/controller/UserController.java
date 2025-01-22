package cloud.icode.onlinesubmit.controller;

import cloud.icode.onlinesubmit.annoation.Log;
import cloud.icode.onlinesubmit.common.ResponseResult;
import cloud.icode.onlinesubmit.constant.UserConstant;
import cloud.icode.onlinesubmit.enums.AppHttpCodeEnum;
import cloud.icode.onlinesubmit.exception.CustomException;
import cloud.icode.onlinesubmit.model.dto.UserLoginRequest;
import cloud.icode.onlinesubmit.model.dto.UserRegisterRequest;
import cloud.icode.onlinesubmit.model.vo.UserVo;
import cloud.icode.onlinesubmit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/5
 * 文件描述: UserController
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "用户模块")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    @Log(name = "用户登录模块")
    public ResponseResult login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        //封装数据
        Map<String, Object> result = userService.userLogin(userLoginRequest, request, response);

        return ResponseResult.okResult(result);
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

    @GetMapping("/getCurrentUser")
    @ApiOperation(value = "获取当前登录用户")
    @Log(name = "获取当前登录用户")
    public ResponseResult getCurrentUser(HttpServletRequest request) {
        //参数校验
        if (request == null) {
            throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
        }

        //从session中获取用户信息
        UserVo userVo = (UserVo) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (userVo == null) {
            return ResponseResult.okResult(null);
        }
        return ResponseResult.okResult(userVo);
    }

}
