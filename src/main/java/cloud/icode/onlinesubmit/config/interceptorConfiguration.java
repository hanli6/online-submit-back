package cloud.icode.onlinesubmit.config;

import cloud.icode.onlinesubmit.interceptor.OnlineSubmitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/11 上午10:57
 * 文件描述: interceptorConfiguration
 */
@Configuration
@RequiredArgsConstructor
public class interceptorConfiguration implements WebMvcConfigurer {

    private final OnlineSubmitInterceptor onlineSubmitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] exclude = new String[]{
                "/user/login/", "/user/register",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/api", "/api-docs", "/api-docs/**", "/doc.html/**"
        };
        registry.addInterceptor(onlineSubmitInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(exclude);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
