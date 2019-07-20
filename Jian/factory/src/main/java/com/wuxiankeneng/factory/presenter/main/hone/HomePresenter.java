package com.wuxiankeneng.factory.presenter.main.hone;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BasePresenter;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.factory.card.Recommend;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.helper.HomeHelper;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import javax.inject.Inject;

public class HomePresenter extends BaseRecyclerPresenter<Shop, HomeContact.View>
        implements HomeContact.Presenter, DataSource.Callback<List<Recommend>> {
    @Inject
    public HomePresenter() {
    }

    //加载推广信息
    @Override
    public void loadRecommend() {
        HomeHelper.loadRecommend(this);
    }

    @Override
    public void loadShops() {

    }


    @Override
    public void onDataLoaded(final List<Recommend> recommends) {

        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                HomeContact.View view = getView();
                if (view != null)
                    view.showRecommend(recommends);
            }
        });


    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                HomeContact.View view = getView();
                if (view != null)
                    view.showError(strRes);
            }
        });
    }
}
