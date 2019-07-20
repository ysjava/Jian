package com.wuxiankeneng.factory.presenter.main.hone;

import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.factory.card.Recommend;
import com.wuxiankeneng.factory.card.RecommendCard;
import com.wuxiankeneng.factory.db.Shop;

import java.util.List;

public interface HomeContact{
    interface View extends BaseContract.RecyclerView<Shop>{
        void showRecommend(List<Recommend> recommendData);
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void loadRecommend();
        void loadShops();
    }
}
