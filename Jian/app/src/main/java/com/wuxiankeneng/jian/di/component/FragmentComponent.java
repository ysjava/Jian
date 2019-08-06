package com.wuxiankeneng.jian.di.component;

import com.wuxiankeneng.jian.di.module.FragmentModule;
import com.wuxiankeneng.jian.di.scope.FragmentScope;
import com.wuxiankeneng.jian.fragment.account.LoginFragment;
import com.wuxiankeneng.jian.fragment.account.RegisterFragment;
import com.wuxiankeneng.jian.fragment.main.HomeFragment;
import com.wuxiankeneng.jian.fragment.search.GoodsSearchFragment;
import com.wuxiankeneng.jian.fragment.search.ShopSearchFragment;


import dagger.Component;

/**
 * Created by White paper on 2019/6/16
 * Describe :
 */
@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(HomeFragment fragment);

    void inject(LoginFragment fragment);

    void inject(RegisterFragment fragment);

    void inject(ShopSearchFragment fragment);

    void inject(GoodsSearchFragment fragment);
}
