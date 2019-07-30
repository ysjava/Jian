package com.wuxiankeneng.factory.presenter.search;

import android.text.TextUtils;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.R;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.helper.SearchHelper;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import javax.inject.Inject;

public class SearchPresenter extends BaseRecyclerPresenter<Shop, SearchContract.View>
        implements SearchContract.Presenter, DataSource.Callback<List<Shop>> {

    @Inject
    public SearchPresenter() {
    }

    @Override
    public void searchShop(String shopName) {
        SearchContract.View view = getView();
        if (TextUtils.isEmpty(shopName)) {
            view.showError(R.string.txt_search_fuck);
        } else
            SearchHelper.searchShop(shopName, this);
    }

    @Override
    public void onDataLoaded(final List<Shop> shops) {
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
