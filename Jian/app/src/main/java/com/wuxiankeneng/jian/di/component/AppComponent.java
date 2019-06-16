package com.wuxiankeneng.jian.di.component;

import com.wuxiankeneng.jian.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

}
