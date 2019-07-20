package com.wuxiankeneng.jian.di.component;

import com.wuxiankeneng.jian.di.module.FragmentModule;
import com.wuxiankeneng.jian.di.scope.FragmentScope;
import com.wuxiankeneng.jian.fragment.main.HomeFragment;


import dagger.Component;

/**
 * Created by White paper on 2019/6/16
 * Describe :
 */
@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(HomeFragment fragment);
}
