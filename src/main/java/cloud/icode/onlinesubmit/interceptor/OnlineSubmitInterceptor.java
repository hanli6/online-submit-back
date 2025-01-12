package cloud.icode.onlinesubmit.interceptor;

import cloud.icode.onlinesubmit.constant.UserConstant;
import cloud.icode.onlinesubmit.enums.AppHttpCodeEnum;
import cloud.icode.onlinesubmit.model.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String requestURI = request.getRequestURI();
        log.info("请求路径：{}",requestURI);

        // 判断是否是登录接口，如果是则直接放行
        if (requestURI.startsWith("/api/user/login") || requestURI.startsWith("/api/user/register")) {
            return true; // 登录和注册请求不进行拦截，直接放行
        }

        //判断是否登录
        UserVo attribute = (UserVo) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (attribute == null) {
            log.info("用户未登录！");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("用户未登录");
            response.setStatus(AppHttpCodeEnum.NEED_LOGIN.getCode());
            return false;
        }
        return true;
    }
}
