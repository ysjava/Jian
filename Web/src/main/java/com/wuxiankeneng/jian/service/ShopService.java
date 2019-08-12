package com.wuxiankeneng.jian.service;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.api.shop.CreateShopModel;
import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.card.ShopCard;
import com.wuxiankeneng.jian.bean.db.Shop;
import com.wuxiankeneng.jian.bean.db.Trader;

import com.wuxiankeneng.jian.factory.ShopFactory;
import com.wuxiankeneng.jian.factory.TraderFactory;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Path("trader/shop")
public class ShopService extends BaseService {
    //开店(创建一个店铺)
    @POST
    @Path("open")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<ShopCard> openShop(CreateShopModel model) {
        if (!CreateShopModel.check(model)) {
            return ResponseModel.buildParameterError();
        }
        //拿到自己
        Trader creator = getTraderSelf();
        Shop shop = ShopFactory.findByName(model.getName().trim());
        if (shop != null) {
            return ResponseModel.buildHaveShopNameError();
        }

        shop = ShopFactory.createShop(creator, model);
        if (shop == null) {
            //创建失败,服务器异常
            return ResponseModel.buildServiceError();
        }

        return ResponseModel.buildOk(new ShopCard(shop));
    }

    //关店(删除)
    @PUT
    @Path("/close/{shopId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel closeShop(@PathParam("shopId") String shopId) {
        if (Strings.isNullOrEmpty(shopId))
            return ResponseModel.buildParameterError();
        //拿到自己
        Trader traderSelf = getTraderSelf();
        Shop shop = ShopFactory.findById(shopId);
        if (shop == null)
            return ResponseModel.buildError("未找到店铺");
        //拿到店铺的创建者
        Trader creator = shop.getCreator();
        if (!Objects.equals(traderSelf.getId(), creator.getId())) {
            //判断shop的创建者是不是自己
            return ResponseModel.buildError("权限不够");
        }
        //数据库进行删除
        ShopFactory.closeShop(shop);

        return ResponseModel.buildOk();

    }

    //设置营业状态
    @PUT
    @Path("/setBusiness/{shopId}|{isBusiness}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<ShopCard> setBusiness(@PathParam("shopId") String shopId, @PathParam("isBusiness") int isBusiness) {
        if (Strings.isNullOrEmpty(shopId))
            return ResponseModel.buildParameterError();
        //用id找店铺
        Shop shop = ShopFactory.findById(shopId);
        if (shop == null)
            return ResponseModel.buildError("没找到店铺");
        //根据传过来的是否营业进行相应设置
        if (isBusiness == 0)
            shop = ShopFactory.stopBusiness(shop);
        else if (isBusiness == 1)
            shop = ShopFactory.startBusiness(shop);
        else
            return ResponseModel.buildParameterError();

        //还没有就是服务器问题了
        if (shop == null)
            return ResponseModel.buildServiceError();
        //完成设置,返回
        return ResponseModel.buildOk(new ShopCard(shop));
    }

//    //暂停营业
//    @PUT
//    @Path("/stop/{shopId}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public ResponseModel<ShopCard> stopBusiness(@PathParam("shopId") String shopId) {
//        if (Strings.isNullOrEmpty(shopId))
//            return ResponseModel.buildParameterError();
//
//        Shop shop = ShopFactory.findById(shopId);
//        if (shop == null)
//            return ResponseModel.buildError("没找到店铺!");
//        shop = ShopFactory.stopBusiness(shop);
//        if (shop == null)
//            return ResponseModel.buildServiceError();
//
//        return ResponseModel.buildOk(new ShopCard(shop));
//    }

}
