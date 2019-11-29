package com.jokerwan.aoploginsample.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by JokerWan on 2019-11-29.
 * Function: 用户点击行为统计注解
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClickBehavior {
    String value();
}
