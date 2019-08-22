package com.wuxiankeneng.factory.presenter.shop;


import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.common.widget.BaseListAdapter;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.card.Recommend;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.model.shop.TypeBean;

import java.util.List;

public interface ShopContract {
    interface View extends BaseContract.View {
        RecyclerAdapter<TypeBean> getTypeAdapter();

        BaseListAdapter<Goods> getGoodsAdapter();
    }

    interface Presenter extends BaseContract.Presenter<View> {

    }

}
