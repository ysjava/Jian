package com.wuxiankeneng.factory.helper;

import android.text.TextUtils;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.R;
import com.wuxiankeneng.factory.card.Recommend;
import com.wuxiankeneng.factory.card.RecommendCard;
import com.wuxiankeneng.factory.card.ShopCard;
import com.wuxiankeneng.factory.card.SimpleShopCard;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.db.SimpleShop;
import com.wuxiankeneng.factory.model.ResponseModel;
import com.wuxiankeneng.factory.net.Network;
import com.wuxiankeneng.factory.net.RemoteService;
import com.wuxiankeneng.factory.presenter.main.hone.HomeContact;

import org.json.JSONArray;
import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeHelper {
    public static void loadRecommend(final DataSource.Callback<List<Recommend>> callback) {

        RemoteService service = Network.remote();
        service.loadRecommend("b271a08d-d5b8-4b15-8422-237315ae5380")
                .enqueue(new Callback<ResponseModel<List<RecommendCard>>>() {
                    @Override
                    public void onResponse(Call<ResponseModel<List<RecommendCard>>> call, Response<ResponseModel<List<RecommendCard>>> response) {
                        ResponseModel<List<RecommendCard>> model = response.body();
                        if (model == null){
                            callback.onDataNotAvailable(R.string.txt_error_server);
                            return;
                        }
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

    //加载店铺(简易)
    public static void loadSimpleShop(String schoolId, final DataSource.Callback<List<SimpleShop>> callback) {
        //TODO 针对学校的外卖平台 所以加载商店的时候就用学生的学校id去找店铺,每个店铺都应该有个学校id
        if (TextUtils.isEmpty(schoolId))
            return;
        //TODO 先本地查,查完进行网络查(不然每次都从网络查的话体验差)
//        loadSimpleShopFormLocal(schoolId);

        RemoteService service = Network.remote();

        service.loadSimpleShop(schoolId).enqueue(new Callback<ResponseModel<List<SimpleShopCard>>>() {

            @Override
            public void onResponse(Call<ResponseModel<List<SimpleShopCard>>> call, Response<ResponseModel<List<SimpleShopCard>>> response) {
                ResponseModel<List<SimpleShopCard>> model = response.body();
                if (model == null){
                    callback.onDataNotAvailable(R.string.txt_error_server);
                    return;
                }
                if (model.success()) {
                    List<SimpleShopCard> simpleShopCards = model.getResult();
                    //转换后的s..shop
                    List<SimpleShop> simpleShops = new ArrayList<>();
                    for (SimpleShopCard simpleShopCard : simpleShopCards) {
                        simpleShops.add(simpleShopCard.build());

                    }
                    callback.onDataLoaded(simpleShops);
                    //保存
//                    LitePal.saveAll(simpleShops);
                    for (SimpleShop simpleShop : simpleShops) {
                        simpleShop.saveOrUpdate();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel<List<SimpleShopCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

    }


    //加载店铺(完整)
    public static void loadShop(String schoolId, final DataSource.Callback<List<Shop>> callback) {
        if (TextUtils.isEmpty(schoolId))
            return;
        RemoteService service = Network.remote();

        service.loadShop(schoolId).enqueue(new Callback<ResponseModel<List<ShopCard>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<ShopCard>>> call, Response<ResponseModel<List<ShopCard>>> response) {
                ResponseModel<List<ShopCard>> model = response.body();
                if (model == null){
                    callback.onDataNotAvailable(R.string.txt_error_server);
                    return;
                }
                if (model.success()) {
                    List<ShopCard> shopCards = model.getResult();
                    List<Shop> shops = new ArrayList<>();
                    for (ShopCard shopCard : shopCards) {
                        Shop shop = shopCard.build();
                        shops.add(shop);
                    }
                    callback.onDataLoaded(shops);
                    //保存
//                    DbHelper.saveA(Shop.class, shops.toArray(new Shop[0]));
                    LitePal.saveAll(shops);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<ShopCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

    }
}
