package com.wuxiankeneng.factory.helper;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.R;
import com.wuxiankeneng.factory.card.OrderCard;
import com.wuxiankeneng.factory.card.ShopCard;
import com.wuxiankeneng.factory.card.SimpleShopCard;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.db.SimpleShop;
import com.wuxiankeneng.factory.model.ResponseModel;
import com.wuxiankeneng.factory.model.order.CreateOrderModel;
import com.wuxiankeneng.factory.net.Network;
import com.wuxiankeneng.factory.net.RemoteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("NullableProblems")
public class ShopHelper {
    //用id找店铺
    public static void getShopById(String shopId, final DataSource.Callback<Shop> callback) {
        RemoteService service = Network.remote();
        service.getShopById(shopId).enqueue(new Callback<ResponseModel<ShopCard>>() {
            @Override
            public void onResponse(Call<ResponseModel<ShopCard>> call, Response<ResponseModel<ShopCard>> response) {
                ResponseModel<ShopCard> model = response.body();
                if (model == null) {
                    callback.onDataNotAvailable(R.string.txt_error_server);
                    return;
                }
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

    //用店铺类型找店铺
    public static void findShopsByType(int type, final DataSource.Callback<List<SimpleShop>> callback) {
        RemoteService service = Network.remote();
        service.findShopsByType(type).enqueue(new Callback<ResponseModel<List<SimpleShopCard>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<SimpleShopCard>>> call, Response<ResponseModel<List<SimpleShopCard>>> response) {
                ResponseModel<List<SimpleShopCard>> model = response.body();
                if (model == null) {
                    callback.onDataNotAvailable(R.string.txt_error_server);
                    return;
                }

                if (model.success()) {
                    List<SimpleShopCard> cardList = model.getResult();
                    //转换后的s..shop
                    List<SimpleShop> simpleShops = new ArrayList<>();
                    for (SimpleShopCard simpleShopCard : cardList) {
                        simpleShops.add(simpleShopCard.build());

                    }
                    if (callback != null)
                        callback.onDataLoaded(simpleShops);
                } else
                    Factory.decodeRspCode(model, callback);

            }

            @Override
            public void onFailure(Call<ResponseModel<List<SimpleShopCard>>> call, Throwable t) {
                if (callback != null)
                    callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    //提交订单
    public static void commit(final CreateOrderModel model, final DataSource.Callback<OrderCard> callback) {
        RemoteService service = Network.remote();
        service.commitOrder(model).enqueue(new Callback<ResponseModel<OrderCard>>() {
            @Override
            public void onResponse(Call<ResponseModel<OrderCard>> call, Response<ResponseModel<OrderCard>> response) {
                ResponseModel<OrderCard> responseModel = response.body();
                if (responseModel == null) {
                    callback.onDataNotAvailable(R.string.txt_error_server);
                    return;
                }
                if (responseModel.success()) {
                    if (callback != null)
                        callback.onDataLoaded(responseModel.getResult());
                } else//解析
                    Factory.decodeRspCode(responseModel, callback);
            }

            @Override
            public void onFailure(Call<ResponseModel<OrderCard>> call, Throwable t) {
                if (callback != null)
                    callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
}
