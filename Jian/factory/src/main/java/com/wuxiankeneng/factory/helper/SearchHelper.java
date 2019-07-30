package com.wuxiankeneng.factory.helper;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.R;
import com.wuxiankeneng.factory.card.ShopCard;
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
}
