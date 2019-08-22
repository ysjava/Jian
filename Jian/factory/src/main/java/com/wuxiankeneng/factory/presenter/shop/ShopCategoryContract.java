package com.wuxiankeneng.factory.presenter.shop;

import com.wuxiankeneng.common.factory.base.BaseContract;

import com.wuxiankeneng.factory.db.SimpleShop;

public interface ShopCategoryContract {
    interface View extends BaseContract.RecyclerView<SimpleShop>{

    }

    interface Presenter extends BaseContract.Presenter<View>{
         void load(int type);
    }
}
