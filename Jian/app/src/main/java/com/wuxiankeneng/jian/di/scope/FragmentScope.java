package com.wuxiankeneng.jian.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
