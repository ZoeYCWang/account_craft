package com.quantil.busi.vo.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/1/24.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Required {
    boolean required() default true;

}
