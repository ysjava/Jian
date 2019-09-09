package com.wuxiankeneng.factory.presenter.main.order;

import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.factory.card.OrderCard;
import com.wuxiankeneng.factory.db.Order;

/**
 * Created by White paper on 2019/9/6
 * Describe :  主页的order fragment的契约
 */
public interface MainOrderContract {
    interface View extends BaseContract.RecyclerView<Order>{
        void preLoadingResult(Order order);
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void preLoadingOrder(String orderId);

    }
}
