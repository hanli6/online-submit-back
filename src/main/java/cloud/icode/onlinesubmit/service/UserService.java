package cloud.icode.onlinesubmit.service;

import cloud.icode.onlinesubmit.model.dto.UserLoginRequest;
import cloud.icode.onlinesubmit.model.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

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
    UserVo userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);
}
