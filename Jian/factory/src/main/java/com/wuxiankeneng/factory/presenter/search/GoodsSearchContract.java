package com.wuxiankeneng.factory.presenter.search;

import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.factory.db.Goods;

public interface GoodsSearchContract {
    interface View extends BaseContract.RecyclerView<Goods> {

    }

    interface Presenter extends BaseContract.Presenter<View> {
        void searchGoods(String goodsName);
    }
}
