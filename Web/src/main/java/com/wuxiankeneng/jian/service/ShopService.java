package com.wuxiankeneng.jian.service;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.api.shop.CreateGoodsModel;
import com.wuxiankeneng.jian.bean.api.shop.CreateShopModel;
import com.wuxiankeneng.jian.bean.api.shop.GoodsCreateResponseModel;
import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.card.GoodsCard;
import com.wuxiankeneng.jian.bean.card.ShopCard;
import com.wuxiankeneng.jian.bean.db.Goods;
import com.wuxiankeneng.jian.bean.db.School;
import com.wuxiankeneng.jian.bean.db.Shop;
import com.wuxiankeneng.jian.bean.db.Trader;

import com.wuxiankeneng.jian.factory.GoodsFactory;
import com.wuxiankeneng.jian.factory.SchoolFactory;
import com.wuxiankeneng.jian.factory.ShopFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.stream.Collectors;

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

        School school = SchoolFactory.findById(model.getSchoolId());
        if (school==null)
            return ResponseModel.buildParameterError();

        shop = ShopFactory.createShop(creator, model,school);
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
            return ResponseModel.buildError("你不能操作当前店铺");
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
        Trader trader = getTraderSelf();
        if (!Objects.equals(trader.getId(), shop.getCreator().getId())) {
            return ResponseModel.buildError("你不能操作当前店铺");
        }
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

    //上架商品
    @POST
    @Path("/create/goods/{shopId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<GoodsCreateResponseModel> createGoodsList(@PathParam("shopId") String shopId, List<CreateGoodsModel> models) {
        if (Strings.isNullOrEmpty(shopId) || models == null || models.size() == 0) {
            return ResponseModel.buildParameterError();
        }

        //遍历检查每个model是否完整
        for (CreateGoodsModel createGoodsModel : models) {
            if (!CreateGoodsModel.check(createGoodsModel))
                return ResponseModel.buildParameterError();
        }


        Shop shop = ShopFactory.findById(shopId);
        if (shop == null)
            return ResponseModel.buildError("没找到店铺");

        Trader trader = getTraderSelf();
        if (!Objects.equals(trader.getId(), shop.getCreator().getId())) {
            return ResponseModel.buildError("你不能操作当前店铺");
        }
        //需添加的商品在本店中已经存在,就把该商品的名字放进这个集合与创建成功的商品一起返回
        List<String> createFailList = new ArrayList<>();
        //存放需创建的商品model (已排除同名商品)
        List<CreateGoodsModel> modelList = new ArrayList<>();
        for (CreateGoodsModel model : models) {
            //拿店铺id和名字去找,如果找到了就添加到一个集合中与添加成功的一起返回
            Goods goods = GoodsFactory.findByNameAndShopId(shop, model.getName());
            if (goods != null)
                createFailList.add(model.getName());
            else
                modelList.add(model);
        }

        if (modelList.size() == 0 && createFailList.size() != 0) {
            //这儿表示需要创建的商品都是已经创建过的,所以创建成功的集合就丢一个空集合
            return ResponseModel.buildOk(new GoodsCreateResponseModel(new ArrayList<>(), createFailList));
        }


        List<Goods> goodsList = ShopFactory.createGoods(shop, modelList);
        if (goodsList == null)
            return ResponseModel.buildServiceError();

        //把goods集合转换成goods card集合
        List<GoodsCard> cardsList = goodsList.stream()
                .map(GoodsCard::new)
                .collect(Collectors.toList());

        return ResponseModel.buildOk(new GoodsCreateResponseModel(cardsList, createFailList));
    }

    //下架商品
    @POST
    @Path("/delete/goods/{shopId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<String>> deleteGoodsList(@PathParam("shopId") String shopId, List<String> goodsIdList) {
        if (Strings.isNullOrEmpty(shopId) || goodsIdList == null || goodsIdList.size() == 0) {
            return ResponseModel.buildParameterError();
        }

        Shop shop = ShopFactory.findById(shopId);
        if (shop == null)
            return ResponseModel.buildError("没找到店铺!");
        Trader trader = getTraderSelf();
        if (!Objects.equals(trader.getId(), shop.getCreator().getId())) {
            return ResponseModel.buildError("你不能操作当前店铺");
        }

        List<String> deleteFailList = new ArrayList<>();

        for (String goodsId : goodsIdList) {
            Goods goods = GoodsFactory.findById(goodsId);
            if (goods == null)
                deleteFailList.add(goodsId);
            else
                GoodsFactory.deleteGoods(goods);
        }

        return ResponseModel.buildOk(deleteFailList);
    }


}
