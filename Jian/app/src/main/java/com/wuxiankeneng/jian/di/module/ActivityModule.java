package com.wuxiankeneng.jian.di.module;

import com.wuxiankeneng.jian.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
@Module
public class ActivityModule {
    private int id;

    public ActivityModule() {
    }

    public ActivityModule(int id) {
        this.id = id;
    }

    @Provides
    public LoginPresenter provideLoginPre() {
        return new LoginPresenter(id);
    }
}
