package cloud.icode.onlinesubmit.aop;

import cloud.icode.onlinesubmit.annoation.Log;
import cloud.icode.onlinesubmit.constant.UserConstant;
import cloud.icode.onlinesubmit.enums.AppHttpCodeEnum;
import cloud.icode.onlinesubmit.exception.CustomException;
import cloud.icode.onlinesubmit.model.OperateLog;
import cloud.icode.onlinesubmit.model.vo.UserVo;
import cloud.icode.onlinesubmit.service.OperateLogService;
import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/9 上午9:26
 * 文件描述: SystemLog
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SystemLog {

    private final OperateLogService operateLogService;

    @Pointcut("@annotation(cloud.icode.onlinesubmit.annoation.Log)")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object log(final ProceedingJoinPoint joinPoint) throws Throwable {
        //获取被增强的类的方法信息
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        //存储到数据库中
        OperateLog operateLog = new OperateLog();

        //获取方法对象
        Method method = methodSignature.getMethod();
        if (method != null) {
            Log annotation = method.getAnnotation(Log.class);
            String moduleName = annotation.name();
            log.info("模块名称：{}", moduleName);
            operateLog.setName(moduleName);
        }

        //方法名称
        String methodName = method.getName();
        log.info("方法名称：{}", methodName);
        operateLog.setMethodName(methodName);

        //获取request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //请求名称
        String requestMethod = request.getMethod();
        log.info("请求方法：{}", requestMethod);
        operateLog.setRequestMethod(requestMethod);

        //请求地址/路径
        String requestURI = request.getRequestURI();
        log.info("请求地址：{}", requestURI);
        operateLog.setUrl(requestURI);

        //访问来源
        String remoteAddr = request.getRemoteAddr();
        log.info("访问IP：{}", remoteAddr);
        operateLog.setIpAddr(remoteAddr);

        //处理时间
        log.info("处理时间：{}", DateUtil.now());
        operateLog.setRequestTime(new Date());

        //用户登录信息
        UserVo userVo = (UserVo) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        log.info("当前用户信息：{}",userVo);
        if (userVo != null) {
            operateLog.setOperator(userVo.getNickname());
        }

        //将日志信息存储到数据库中
        int count = operateLogService.saveLog(operateLog);
        if (count==0){
            throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
        }
        log.info("日志存储成功！");

        return joinPoint.proceed();
    }
}
