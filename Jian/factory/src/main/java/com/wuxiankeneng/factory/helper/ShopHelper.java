package com.wuxiankeneng.factory.helper;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.R;
import com.wuxiankeneng.factory.card.ShopCard;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.model.ResponseModel;
import com.wuxiankeneng.factory.net.Network;
import com.wuxiankeneng.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopHelper {
    public static void getShopById(String shopId, final DataSource.Callback<Shop> callback) {
        RemoteService service = Network.remote();
        service.getShopById(shopId).enqueue(new Callback<ResponseModel<ShopCard>>() {
            @Override
            public void onResponse(Call<ResponseModel<ShopCard>> call, Response<ResponseModel<ShopCard>> response) {
                ResponseModel<ShopCard> model = response.body();
                assert model != null;
                if (model.success()) {
                    ShopCard card = model.getResult();
                    if (callback != null) {
                        callback.onDataLoaded(card.build());
                    }
                } else {
                    if (callback != null) {
                        Factory.decodeRspCode(model, callback);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<ShopCard>> call, Throwable t) {
                if (callback != null)
                    callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
}
