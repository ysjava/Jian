package com.wuxiankeneng.factory.presenter.search;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.factory.card.SearchShopCard;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.helper.SearchHelper;
import com.wuxiankeneng.factory.presenter.shop.ShopContract;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import javax.inject.Inject;

public class ShopSearchPresenter extends BaseRecyclerPresenter<SearchShopCard, ShopSearchContract.View>
        implements ShopSearchContract.Presenter, DataSource.Callback<List<SearchShopCard>> {

    @Inject
    public ShopSearchPresenter() {
    }

    @Override
    public void searchShop(String shopName) {
        SearchHelper.searchShop(shopName.trim(), this);
    }


    @Override
    public void onDataLoaded(final List<SearchShopCard> shops) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                refreshData(shops);
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                getView().showError(strRes);
            }
        });
    }
}
