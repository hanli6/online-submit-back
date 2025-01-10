package cloud.icode.onlinesubmit.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/9 上午9:20
 * 文件描述: 自定义日志注解
 */
@Target(value = {ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 模块名称
     * @return
     */
    public String name() default "";
}
