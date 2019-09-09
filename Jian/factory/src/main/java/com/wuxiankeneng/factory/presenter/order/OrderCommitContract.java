package com.wuxiankeneng.factory.presenter.order;

import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.factory.card.OrderCard;
import com.wuxiankeneng.factory.model.order.CreateOrderModel;

/**
 * Created by White paper on 2019/9/2
 * Describe :
 */
public interface OrderCommitContract {
    interface View extends BaseContract.View{
        void success(OrderCard orderCard);
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void commit(CreateOrderModel model);
    }
}
