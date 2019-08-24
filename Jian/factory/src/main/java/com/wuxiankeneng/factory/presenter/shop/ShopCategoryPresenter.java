package com.wuxiankeneng.factory.presenter.shop;

import android.support.v7.util.DiffUtil;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.card.SimpleShopCard;
import com.wuxiankeneng.factory.db.SimpleShop;
import com.wuxiankeneng.factory.helper.ShopHelper;
import com.wuxiankeneng.factory.tools.DiffUiDataCallback;


import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import javax.inject.Inject;

public class ShopCategoryPresenter extends BaseRecyclerPresenter<SimpleShopCard, ShopCategoryContract.View>
        implements ShopCategoryContract.Presenter, DataSource.Callback<List<SimpleShop>> {

    @Inject
    public ShopCategoryPresenter() {
    }

    @Override
    public void load(int type) {
        super.start();
        ShopHelper.findShopsByType(type, this);
    }

    @Override
    public void onDataLoaded(final List<SimpleShop> shops) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                ShopCategoryContract.View view = getView();
                if (view == null)
                    return;
                //拿到商店列表的适配器
                RecyclerAdapter<SimpleShop> adapter = view.getRecyclerAdapter();
                //拿到旧数据
                List<SimpleShop> oldShops = adapter.getItems();
                //进行数据对比
                DiffUtil.Callback callback = new DiffUiDataCallback<>(oldShops, shops);
                DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
                // 改变数据集合并不通知界面刷新
                adapter.getItems().clear();
                adapter.getItems().addAll(shops);
                // 进行增量更新
                result.dispatchUpdatesTo(adapter);

                view.onAdapterDataChanged();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                ShopCategoryContract.View view = getView();
                if (view != null) {
                    view.showError(strRes);
                }
            }
        });
    }
}
