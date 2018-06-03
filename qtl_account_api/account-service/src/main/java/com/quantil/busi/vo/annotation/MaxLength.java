package com.quantil.busi.vo.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/1/24.
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MaxLength {
    int length() default 36;
}
