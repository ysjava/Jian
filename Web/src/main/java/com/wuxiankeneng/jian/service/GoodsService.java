package com.wuxiankeneng.jian.service;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.card.GoodsCard;
import com.wuxiankeneng.jian.bean.card.SimpleGoodsCard2;
import com.wuxiankeneng.jian.bean.db.Goods;
import com.wuxiankeneng.jian.bean.db.School;
import com.wuxiankeneng.jian.bean.db.Shop;
import com.wuxiankeneng.jian.bean.db.Student;
import com.wuxiankeneng.jian.factory.GoodsFactory;
import com.wuxiankeneng.jian.factory.ShopFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("student")
public class GoodsService extends BaseService {

    //拿商品,用日销量去拿
    @GET
    @Path("getGoods/byDailySales")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<Goods>> getGoodsByDailySales() {
        Student student = getStudentSelf();

        School school = student.getSchool();
        List<Goods> goodsList = GoodsFactory.find(school);
        if (goodsList == null)
            return ResponseModel.buildServiceError();

        return ResponseModel.buildOk(goodsList);
    }

    //在店铺中搜索商品
    @GET
    @Path("searchGoods/inShop/{goodsName}|{shopId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<GoodsCard>> searchGoodsInShop(@PathParam("goodsName") String goodsName,
                                                            @PathParam("shopId") String shopId) {
        if (Strings.isNullOrEmpty(goodsName) || Strings.isNullOrEmpty(shopId))
            return ResponseModel.buildParameterError();

        Shop shop = ShopFactory.findById(shopId);
        if (shop == null)
            return ResponseModel.buildError("没找到店铺");

        List<Goods> goodsList = GoodsFactory.searchGoodsInShop(goodsName.trim(), shop);
        if (goodsList == null)
            return ResponseModel.buildServiceError();

        return ResponseModel.buildOk(goodsList
                .stream()
                .map(GoodsCard::new)
                .collect(Collectors.toList()));
    }

}
