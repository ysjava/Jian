package com.wuxiankeneng.factory.presenter.shop;

import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BasePresenter;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.common.widget.BaseListAdapter;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.helper.ShopHelper;
import com.wuxiankeneng.factory.model.shop.TypeBean;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ShopPresenter extends BasePresenter<ShopContract.View>
        implements DataSource.Callback<Shop> {
    private String shopId;

    @Inject
    public ShopPresenter(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public void start() {
        super.start();

        ShopHelper.getShopById(shopId, this);
    }

    @Override
    public void onDataLoaded(final Shop shop) {

        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Application.showToast(shopId);

                ShopContract.View view = getView();
                if (view == null)
                    return;
                BaseListAdapter<Goods> adapter = view.getGoodsAdapter();
                RecyclerAdapter<TypeBean> typeAdapter = view.getTypeAdapter();
                List<TypeBean> typeBeans = new ArrayList<>();

                ArrayList<String> type = new ArrayList<>();

                for (Goods good : shop.getAllGoods()) {
                    if (!type.contains(good.getType())) {
                        type.add(good.getType());
                        typeBeans.add(new TypeBean(good.getType(), good.getTypeId()));
                    }
                }

                typeAdapter.replace(typeBeans);
                //网络拿回来的商品集合
                adapter.replace(shop.getAllGoods());

            }
        });
    }

    @Override
    public void onDataNotAvailable(int strRes) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {

            }
        });
    }
}
