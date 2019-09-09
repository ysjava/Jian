package com.wuxiankeneng.factory.net;


import com.wuxiankeneng.factory.card.AddressCard;
import com.wuxiankeneng.factory.card.GoodsCard;
import com.wuxiankeneng.factory.card.OrderCard;
import com.wuxiankeneng.factory.card.RecommendCard;
import com.wuxiankeneng.factory.card.SearchShopCard;
import com.wuxiankeneng.factory.card.ShopCard;

import com.wuxiankeneng.factory.card.SimpleShopCard;
import com.wuxiankeneng.factory.model.ResponseModel;
import com.wuxiankeneng.factory.model.account.AccountRspModel;
import com.wuxiankeneng.factory.model.account.LoginModel;
import com.wuxiankeneng.factory.model.account.RegisterModel;
import com.wuxiankeneng.factory.model.order.CreateOrderModel;

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
    @POST("account/register/student")
    Call<ResponseModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 登录接口
     *
     * @param model LoginModel
     * @return RspModel<AccountRspModel>
     */
    @POST("account/login/student")
    Call<ResponseModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 绑定设备Id
     *
     * @param pushId 设备Id
     * @return RspModel<AccountRspModel>
     */
    @POST("account/bind/student/{pushId}")
    Call<ResponseModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);

    /**
     * 获取推荐列表
     *
     * @param shopId 推广链接地址
     * @return RspModel<AccountRspModel>
     */
    @GET("student/loadRecommend/{shopId}")
    Call<ResponseModel<List<RecommendCard>>> loadRecommend(@Path("shopId") String shopId);

    /**
     * 获取商店列表 (简单的)
     *
     * @param schoolId 学校id
     * @return ResponseModel<List<SimpleShopCard>>
     */
    @GET("student/getSimpleShops/{schoolId}")
    Call<ResponseModel<List<SimpleShopCard>>> loadSimpleShop(@Path("schoolId") String schoolId);

    /**
     * 获取商店列表
     *
     * @param schoolId 学校id
     * @return ResponseModel<List<ShopCard>>
     */
    @GET("student/getShops/{schoolId}")
    Call<ResponseModel<List<ShopCard>>> loadShop(@Path("schoolId") String schoolId);

    /**
     * 搜索商店
     *
     * @param shopName 商店名字
     * @return ResponseModel<List<ShopCard>>
     */
    @GET("student/searchShops/{shopName}")
    Call<ResponseModel<List<SearchShopCard>>> searchShop(@Path("shopName") String shopName);

    /**
     * 在店铺搜索商品
     *
     * @param goodsName 商品名字
     * @return ResponseModel<List<GoodsCard>>
     */
    @GET("student/searchGoods/inShop/{goodsName}|{shopId}")
    Call<ResponseModel<List<GoodsCard>>> searchGoods(@Path("goodsName") String goodsName, @Path("shopId") String shopId);

    /**
     * 用店铺id获取店铺信息
     *
     * @param shopId 店铺id
     * @return ResponseModel<ShopCard>
     */
    @GET("student/getShop/{shopId}")
    Call<ResponseModel<ShopCard>> getShopById(@Path("shopId") String shopId);

    /**
     * 用类型找到同类型店铺集合
     *
     * @param type 店铺类型
     * @return ResponseModel<List<SimpleShopCard>>>
     */
    @GET("student/findShopsByType/{type}")
    Call<ResponseModel<List<SimpleShopCard>>> findShopsByType(@Path("type") int type);
    /**
     * 拿到地址集合
     * @return ResponseModel<List<SimpleShopCard>>>
     */
    @GET("student/getAddressList")
    Call<ResponseModel<List<AddressCard>>> getAddressList();

    /**
     * 提交订单
     *
     * @param model 订单model
     * @return ResponseModel<OrderCard>
     */
    @POST("student/commitOrder")
    Call<ResponseModel<OrderCard>> commitOrder(@Body CreateOrderModel model);

    /**
     * 获取订单集
     *
     * @return ResponseModel<OrderCard>
     */
    @GET("student/getOrders")
    Call<ResponseModel<List<OrderCard>>> getOrders();

    /**
     * 用id获取订单信息  一个
     *
     * @return ResponseModel<OrderCard>
     */
    @GET("student/getOrder/{orderId}")
    Call<ResponseModel<OrderCard>> getOrderById(@Path("orderId") String orderId);


}
