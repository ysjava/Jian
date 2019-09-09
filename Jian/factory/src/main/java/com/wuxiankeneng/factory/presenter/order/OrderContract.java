package com.wuxiankeneng.factory.presenter.order;

import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.factory.card.GoodsCard;
import com.wuxiankeneng.factory.db.Order;

/**
 * Created by White paper on 2019/9/9
 * Describe :
 */
public interface OrderContract {
    interface View extends BaseContract.RecyclerView<GoodsCard>{

    }

    interface Presenter extends BaseContract.Presenter<View>{

    }
}
