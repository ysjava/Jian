package com.wuxiankeneng.jian.di.component;


import com.wuxiankeneng.jian.LoginActivity;
import com.wuxiankeneng.jian.RegisterActivity;
import com.wuxiankeneng.jian.activity.SearchActivity;
import com.wuxiankeneng.jian.activity.ShopActivity;
import com.wuxiankeneng.jian.di.module.ActivityModule;
import com.wuxiankeneng.jian.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    void inject(SearchActivity activity);

    void inject(ShopActivity activity);
}
