package com.wuxiankeneng.jian.di.component;


import com.wuxiankeneng.jian.activity.AddressActivity;
import com.wuxiankeneng.jian.activity.OrderActivity;
import com.wuxiankeneng.jian.activity.OrderCommitActivity;
import com.wuxiankeneng.jian.activity.SearchActivity;
import com.wuxiankeneng.jian.activity.ShopActivity;
import com.wuxiankeneng.jian.activity.ShopCategoryActivity;
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


//    void inject(SearchActivity activity);

    void inject(ShopActivity activity);

    void inject(ShopCategoryActivity activity);

    void inject(OrderCommitActivity activity);

    void inject(AddressActivity activity);

    void inject(OrderActivity activity);

}
