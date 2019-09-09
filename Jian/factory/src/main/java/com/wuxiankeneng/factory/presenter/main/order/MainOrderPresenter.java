package com.wuxiankeneng.factory.presenter.main.order;

import android.support.v7.util.DiffUtil;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.db.Order;
import com.wuxiankeneng.factory.helper.OrderHelper;
import com.wuxiankeneng.factory.tools.DiffUiDataCallback;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by White paper on 2019/9/6
 * Describe :
 */
public class MainOrderPresenter extends BaseRecyclerPresenter<Order, MainOrderContract.View>
        implements MainOrderContract.Presenter, DataSource.Callback<List<Order>> {
    @Inject
    public MainOrderPresenter() {
    }

    @Override
    public void start() {
        super.start();
        OrderHelper.loadOrders(this);
    }

    @Override
    public void onDataLoaded(final List<Order> orders) {
        final MainOrderContract.View view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (view == null)
                    return;
                RecyclerAdapter<Order> adapter = view.getRecyclerAdapter();
                //拿到旧数据
                List<Order> oldList = adapter.getItems();
                //进行数据对比
                DiffUtil.Callback callback = new DiffUiDataCallback<>(oldList, orders);
                DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

                adapter.replace(orders);
                //进行增量更新
                result.dispatchUpdatesTo(adapter);

                view.onAdapterDataChanged();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final MainOrderContract.View view = getView();
        Run.onUiSync(new Action() {
            @Override
            public void call() {
                if (view != null)
                    view.showError(strRes);
            }
        });
    }

    @Override
    public void preLoadingOrder(String orderId) {
        OrderHelper.preLoadingOrder(orderId, new DataSource.Callback<Order>() {
            @Override
            public void onDataNotAvailable(final int strRes) {
                Run.onUiSync(new Action() {
                    @Override
                    public void call() {
                        final MainOrderContract.View view = getView();
                        view.showError(strRes);
                    }
                });
            }

            @Override
            public void onDataLoaded(final Order order) {
                Run.onUiSync(new Action() {
                    @Override
                    public void call() {
                        final MainOrderContract.View view = getView();
                        view.preLoadingResult(order);
                    }
                });
            }
        });
    }
}
