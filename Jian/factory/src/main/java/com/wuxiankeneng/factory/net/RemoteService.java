package com.wuxiankeneng.factory.net;


import com.wuxiankeneng.factory.card.GoodsCard;
import com.wuxiankeneng.factory.card.Recommend;
import com.wuxiankeneng.factory.card.RecommendCard;
import com.wuxiankeneng.factory.card.ShopCard;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.model.ResponseModel;
import com.wuxiankeneng.factory.model.account.AccountRspModel;
import com.wuxiankeneng.factory.model.account.LoginModel;
import com.wuxiankeneng.factory.model.account.RegisterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 网络请求的所有的接口
 *
 * @version 1.0.0
 */
public interface RemoteService {

    /**
     * 注册接口
     *
     * @param model 传入的是RegisterModel
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<ResponseModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 登录接口
     *
     * @param model LoginModel
     * @return RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<ResponseModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 绑定设备Id
     *
     * @param pushId 设备Id
     * @return RspModel<AccountRspModel>
     */
    @POST("account/bind/{pushId}")
    Call<ResponseModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);

    /**
     * 获取推荐列表
     *
     * @param url 推广链接地址
     * @return RspModel<AccountRspModel>
     */
    @GET("user/recommend/{url}")
    Call<ResponseModel<List<RecommendCard>>> loadRecommend(@Path("url") String url);

    /**
     * 获取商店列表
     *
     * @param schoolId 学校id
     * @return RspModel<AccountRspModel>
     */
    @GET("shops/{schoolId}")
    Call<ResponseModel<List<ShopCard>>> loadShop(@Path("schoolId") String schoolId);

    /**
     * 搜索商店
     *
     * @param shopName 商店名字
     * @return RspModel<AccountRspModel>
     */
    @GET("shops/search/{shopName}")
    Call<ResponseModel<List<ShopCard>>> searchShop(@Path("shopName") String shopName);

    /**
     * 搜索商品
     *
     * @param goodsName 商品名字
     * @return RspModel<AccountRspModel>
     */
    @GET("goods/search/{goodsName}")
    Call<ResponseModel<List<GoodsCard>>> searchGoods(@Path("goodsName") String goodsName);

    /**
     * 用店铺id获取店铺信息
     *
     * @param shopId 店铺id
     * @return RspModel<AccountRspModel>
     */
    @GET("shop/{shopId}")
    Call<ResponseModel<ShopCard>> getShopById(@Path("shopId") String shopId);
}
