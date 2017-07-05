package com.netease.hearttouch.htrecyclerviewadapter.bga;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zyl06 on 10/15/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BGARecyclerViewHolderAnnotation {
    int leftLayoutId() default 0;
    int rightLayoutId() default 0;
}