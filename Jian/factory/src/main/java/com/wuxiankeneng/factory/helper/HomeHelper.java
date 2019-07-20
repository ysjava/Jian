package com.wuxiankeneng.factory.helper;

import android.text.TextUtils;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.R;
import com.wuxiankeneng.factory.card.Recommend;
import com.wuxiankeneng.factory.card.RecommendCard;
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

public class HomeHelper {
    public static void loadRecommend(final DataSource.Callback<List<Recommend>> callback) {
        //推广应该有个固定链接
        String url = "";
        RemoteService service = Network.remote();
        service.loadRecommend(url).enqueue(new Callback<ResponseModel<List<RecommendCard>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<RecommendCard>>> call, Response<ResponseModel<List<RecommendCard>>> response) {
                ResponseModel<List<RecommendCard>> model = response.body();
                assert model != null;
                if (model.success()) {
                    List<RecommendCard> cards = model.getResult();
                    List<Recommend> recommends = new ArrayList<>();
                    for (RecommendCard card : cards) {
                        recommends.add(card.buildRecommend());
                    }
                    callback.onDataLoaded(recommends);
                } else {
                    Factory.decodeRspCode(model, callback);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<RecommendCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    //加载店铺
    public static void loadShop(String schoolId,DataSource.Callback<ShopCard> callback){
        //TODO 针对学校的外卖平台 所以加载商店的时候就用学生的学校id去找店铺,每个店铺都应该有个学校id
        if (TextUtils.isEmpty(schoolId))
            return;
        RemoteService service = Network.remote();
        service.loadShop(schoolId).enqueue(new Callback<ResponseModel<List<ShopCard>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<ShopCard>>> call, Response<ResponseModel<List<ShopCard>>> response) {
                ResponseModel<List<ShopCard>> model = response.body();
                assert model != null;
                if (model.success()){
                    List<ShopCard> shops = model.getResult();

                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<ShopCard>>> call, Throwable t) {

            }
        });

    }
}
