package com.wuxiankeneng.jian.factory;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.api.shop.CreateGoodsModel;
import com.wuxiankeneng.jian.bean.api.shop.CreateShopModel;
import com.wuxiankeneng.jian.bean.db.Goods;
import com.wuxiankeneng.jian.bean.db.School;
import com.wuxiankeneng.jian.bean.db.Shop;
import com.wuxiankeneng.jian.bean.db.Trader;
import com.wuxiankeneng.jian.utils.Hib;

import java.util.ArrayList;
import java.util.List;

public class ShopFactory {

    public static Shop findByName(String name) {
        return Hib.query(session -> (Shop) session
                .createQuery("from Shop where name=:name")
                .setParameter("name", name)
                .uniqueResult());
    }

    public static Shop findById(String shopId) {
        return Hib.query(session -> (Shop) session
                .createQuery("from Shop where id=:shopId")
                .setParameter("shopId", shopId)
                .uniqueResult());
    }

    //开店
    public static Shop createShop(Trader creator, CreateShopModel model, School school) {
        return Hib.query(session -> {
            Shop shop = new Shop(creator, model, school);
            session.save(shop);
            return shop;
        });

    }

    //关店(删除)
    public static void closeShop(Shop shop) {
        Hib.queryOnly(session -> session.delete(shop));
    }

    //设置营业状态为是
    public static Shop startBusiness(Shop shop) {
        return Hib.query(session -> {
            shop.setBusiness(true);
            session.saveOrUpdate(shop);
            return shop;
        });
    }

    //设置营业状态为否
    public static Shop stopBusiness(Shop shop) {
        return Hib.query(session -> {
            shop.setBusiness(false);
            session.saveOrUpdate(shop);
            return shop;
        });
    }

    public static List<Goods> createGoods(Shop shop, List<CreateGoodsModel> models) {
        return Hib.query(session -> {
            List<Goods> goodsList = new ArrayList<>();
            for (CreateGoodsModel model : models) {
                Goods goods = new Goods(shop, model);
                session.save(goods);
                goodsList.add(goods);
            }
            return goodsList;
        });

    }

    //学生用学校id拿到店铺集合
    public static List<Shop> getShopsBySchoolId(School school) {
        return Hib.query(session -> {
            session.load(school, school.getId());
            return new ArrayList<>(school.getShops());
        });
    }
}
