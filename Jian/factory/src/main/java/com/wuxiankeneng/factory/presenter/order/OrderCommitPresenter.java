package com.wuxiankeneng.factory.presenter.order;

import android.util.Log;

import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BasePresenter;
import com.wuxiankeneng.factory.card.OrderCard;
import com.wuxiankeneng.factory.helper.ShopHelper;
import com.wuxiankeneng.factory.model.order.CreateOrderModel;

import javax.inject.Inject;

/**
 * Created by White paper on 2019/9/
 * Describe :
 */
public class OrderCommitPresenter extends BasePresenter<OrderCommitContract.View>
        implements OrderCommitContract.Presenter, DataSource.Callback<OrderCard> {

    @Inject
    public OrderCommitPresenter() {
    }


    @Override
    public void commit(CreateOrderModel model) {
        ShopHelper.commit(model, this);
    }

    @Override
    public void onDataLoaded(OrderCard orderCard) {
        //到这儿就表示支付成功并提交订单了
        if (getView() != null)
            getView().success(orderCard);

        Log.e("BAN", orderCard.toString());
    }

    @Override
    public void onDataNotAvailable(int strRes) {

    }
}
