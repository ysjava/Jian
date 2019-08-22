package com.wuxiankeneng.factory.presenter.main.hone;

import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.factory.card.Recommend;
import com.wuxiankeneng.factory.card.RecommendCard;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.db.SimpleGoods;
import com.wuxiankeneng.factory.db.SimpleShop;
import com.wuxiankeneng.factory.tools.DiffUiDataCallback;

import java.util.List;
import java.util.Objects;

public interface HomeContact{
    interface View extends BaseContract.RecyclerView<SimpleShop>{
        void showRecommend(List<Recommend> recommendData);
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void loadRecommend();
        void loadSimpleShops();
    }
}
