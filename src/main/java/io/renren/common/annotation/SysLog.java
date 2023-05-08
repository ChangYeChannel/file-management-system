package io.renren.common.annotation;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
	//当前方法操作类型
	String code() default "";

	//当前方法操作内容
	String msg() default "";
}
