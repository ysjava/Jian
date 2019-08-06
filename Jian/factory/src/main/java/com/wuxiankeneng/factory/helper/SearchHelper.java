package com.wuxiankeneng.factory.helper;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.R;
import com.wuxiankeneng.factory.card.GoodsCard;
import com.wuxiankeneng.factory.card.ShopCard;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.model.ResponseModel;
import com.wuxiankeneng.factory.net.Network;
import com.wuxiankeneng.factory.net.RemoteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchHelper {
    public static void searchShop(String shopName, final DataSource.Callback<List<Shop>> callback) {
        RemoteService service = Network.remote();
        service.searchShop(shopName).enqueue(new Callback<ResponseModel<List<ShopCard>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<ShopCard>>> call, Response<ResponseModel<List<ShopCard>>> response) {
                ResponseModel<List<ShopCard>> model = response.body();
                assert model != null;
                if (model.success()) {
                    List<ShopCard> cards = model.getResult();
                    List<Shop> shops = new ArrayList<>();
                    for (ShopCard card : cards) {
                        shops.add(card.build());
                    }
                    if (callback != null)
                        callback.onDataLoaded(shops);
                } else {
                    if (callback != null)
                        Factory.decodeRspCode(model, callback);
                }

            }

            @Override
            public void onFailure(Call<ResponseModel<List<ShopCard>>> call, Throwable t) {
                if (callback != null)
                    callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    public static void searchGoods(String goodsName, final DataSource.Callback<List<Goods>> callback) {
        RemoteService service = Network.remote();
        service.searchGoods(goodsName).enqueue(new Callback<ResponseModel<List<GoodsCard>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<GoodsCard>>> call, Response<ResponseModel<List<GoodsCard>>> response) {
                ResponseModel<List<GoodsCard>> model = response.body();
                assert model != null;
                if (model.success()) {
                    List<GoodsCard> cards = model.getResult();
                    List<Goods> goods = new ArrayList<>();
                    for (GoodsCard card : cards) {
                        goods.add(card.build());
                    }
                    if (callback != null)
                        callback.onDataLoaded(goods);
                } else {
                    if (callback != null)
                        Factory.decodeRspCode(model, callback);
                }

            }

            @Override
            public void onFailure(Call<ResponseModel<List<GoodsCard>>> call, Throwable t) {
                if (callback != null)
                    callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

}
