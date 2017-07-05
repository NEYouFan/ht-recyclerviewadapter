package com.netease.hearttouch.htrecyclerviewadapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *recycleviewholder注解
 * Created by zhengwen on 15-6-16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TRecyclerViewHolderAnnotation {
    public int resId() default 0;
}


