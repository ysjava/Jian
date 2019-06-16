package com.wuxiankeneng.jian.di.component;

import com.wuxiankeneng.jian.di.scope.ActivityScope;
import com.wuxiankeneng.jian.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by White paper on 2019/6/16
 * Describe :
 */
@FragmentScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {
}
