package com.wuxiankeneng.jian.di.module;

import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.jian.di.component.AppComponent;

import dagger.Module;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }
}
