package com.wuxiankeneng.factory.presenter.search;

import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.db.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsSearchContract {
    interface View extends BaseContract.RecyclerView<Goods> {
        Map<String,Goods> getSelectedGoodsList();
        //刷新购物车
        void updateShopping();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void searchGoods(String goodsName);
    }
}
