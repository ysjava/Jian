package com.wuxiankeneng.factory.presenter.main.hone;

import android.support.v7.util.DiffUtil;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BasePresenter;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.card.Recommend;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.db.SimpleShop;
import com.wuxiankeneng.factory.helper.HomeHelper;
import com.wuxiankeneng.factory.tools.DiffUiDataCallback;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import javax.inject.Inject;

public class HomePresenter extends BaseRecyclerPresenter<SimpleShop, HomeContact.View>
        implements HomeContact.Presenter, DataSource.Callback<List<Recommend>> {
    @Inject
    public HomePresenter() {
    }

    //加载推广信息
    @Override
    public void loadRecommend() {
        HomeHelper.loadRecommend(this);
    }

    //加载商店信息
    @Override
    public void loadSimpleShops() {
        //TODO 登陆后保存学校id到v本地
        HomeHelper.loadSimpleShop("b271a08d-d5b8-4b15-8422-237315ae5380", new DataSource.Callback<List<SimpleShop>>() {
            @Override
            public void onDataNotAvailable(final int strRes) {
                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                        HomeContact.View view = getView();
                        if (view != null) {
                            view.showError(strRes);
                        }
                    }
                });
            }

            @Override
            public void onDataLoaded(final List<SimpleShop> shops) {
                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                        HomeContact.View view = getView();
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
        });
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
