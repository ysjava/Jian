package com.wuxiankeneng.jian.di.module;

//import com.wuxiankeneng.jian.LoginPresenter;

import com.wuxiankeneng.factory.presenter.shop.ShopPresenter;
import com.wuxiankeneng.jian.R;

import dagger.Module;
import dagger.Provides;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
@Module
public class ActivityModule {
    private String shopId;

    public ActivityModule() {
    }

    public ActivityModule(String shopId) {
        this.shopId = shopId;
    }

//    @Provides
//    public LoginPresenter provideLoginPre() {
//        return new LoginPresenter(id);
//    }

    @Provides
    public ShopPresenter provideShopPre() {
        return new ShopPresenter(shopId);
    }
}
