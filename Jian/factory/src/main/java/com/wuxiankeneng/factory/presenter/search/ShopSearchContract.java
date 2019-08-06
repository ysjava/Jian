package com.wuxiankeneng.factory.presenter.search;

import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.factory.db.Shop;

public interface ShopSearchContract {
    interface View extends BaseContract.RecyclerView<Shop> {

    }

    interface Presenter extends BaseContract.Presenter<View> {
        void searchShop(String shopName);
    }
}
