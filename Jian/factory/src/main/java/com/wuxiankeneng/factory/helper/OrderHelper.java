package com.wuxiankeneng.factory.helper;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.R;
import com.wuxiankeneng.factory.card.OrderCard;
import com.wuxiankeneng.factory.db.Order;
import com.wuxiankeneng.factory.model.ResponseModel;
import com.wuxiankeneng.factory.net.Network;
import com.wuxiankeneng.factory.net.RemoteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by White paper on 2019/9/7
 * Describe :
 */
public class OrderHelper {
    public static void loadOrders(final DataSource.Callback<List<Order>> callback) {
        RemoteService service = Network.remote();
        service.getOrders().enqueue(new Callback<ResponseModel<List<OrderCard>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<OrderCard>>> call, Response<ResponseModel<List<OrderCard>>> response) {
                ResponseModel<List<OrderCard>> responseModel = response.body();
                if (responseModel == null) {
                    callback.onDataNotAvailable(R.string.txt_error_server);
                    return;
                }
                if (responseModel.success()) {
                    List<OrderCard> cards = responseModel.getResult();
                    List<Order> orders = new ArrayList<>();
                    for (OrderCard card : cards) {
                        orders.add(card.build());
                    }
                    callback.onDataLoaded(orders);
                } else
                    Factory.decodeRspCode(responseModel, callback);
            }

            @Override
            public void onFailure(Call<ResponseModel<List<OrderCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    public static void preLoadingOrder(String orderId, final DataSource.Callback<Order> callback) {
        RemoteService service = Network.remote();
        service.getOrderById(orderId).enqueue(new Callback<ResponseModel<OrderCard>>() {
            @Override
            public void onResponse(Call<ResponseModel<OrderCard>> call, Response<ResponseModel<OrderCard>> response) {
                ResponseModel<OrderCard> responseModel = response.body();
                if (responseModel == null) {
                    callback.onDataNotAvailable(R.string.txt_error_server);
                    return;
                }
                if (responseModel.success()){
                    OrderCard card = responseModel.getResult();
                    callback.onDataLoaded(card.build());
                }else
                    Factory.decodeRspCode(responseModel,callback);

            }

            @Override
            public void onFailure(Call<ResponseModel<OrderCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
}
