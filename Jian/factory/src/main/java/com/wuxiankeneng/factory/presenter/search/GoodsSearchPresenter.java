package com.wuxiankeneng.factory.presenter.search;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BasePresenter;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.helper.SearchHelper;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import javax.inject.Inject;

public class GoodsSearchPresenter extends BaseRecyclerPresenter<Goods, GoodsSearchContract.View>
        implements GoodsSearchContract.Presenter, DataSource.Callback<List<Goods>> {
    @Inject
    public GoodsSearchPresenter() {
    }

    @Override
    public void searchGoods(String goodsName) {
        SearchHelper.searchGoods(goodsName, this);
    }


    @Override
    public void onDataLoaded(final List<Goods> goods) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                refreshData(goods);
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
