package cloud.icode.onlinesubmit.interceptor;

import cloud.icode.onlinesubmit.constant.UserConstant;
import cloud.icode.onlinesubmit.model.vo.UserVo;
import cloud.icode.onlinesubmit.utils.UserContext;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/11 上午10:46
 * 文件描述: onlineSubmitInterceptor
 */
@Component
@Slf4j
public class OnlineSubmitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从session中获取当前用户信息
        UserVo userVo = (UserVo) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (userVo == null) {
            log.info("用户未登录");
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().write("用户未登录");
            response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
            return false;
        }else {
            UserContext.setUser(userVo.getId());
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}
