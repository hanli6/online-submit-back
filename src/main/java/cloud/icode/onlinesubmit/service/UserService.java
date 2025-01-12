package cloud.icode.onlinesubmit.service;

import cloud.icode.onlinesubmit.model.dto.UserLoginRequest;
import cloud.icode.onlinesubmit.model.dto.UserRegisterRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/5
 * 文件描述: UserService
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    Map<String,Object> userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    int registerUser(UserRegisterRequest userRegisterRequest);
}
