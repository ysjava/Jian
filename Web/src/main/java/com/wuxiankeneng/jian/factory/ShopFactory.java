package com.wuxiankeneng.jian.factory;

import com.wuxiankeneng.jian.bean.api.shop.CreateGoodsModel;
import com.wuxiankeneng.jian.bean.api.shop.CreateShopModel;
import com.wuxiankeneng.jian.bean.card.SearchShopCard;
import com.wuxiankeneng.jian.bean.card.SimpleGoodsCard;
import com.wuxiankeneng.jian.bean.card.SimpleShopCard;
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

    public static Shop load(Shop shop) {
        return Hib.query(session -> {
            //因为店铺的菜品集合是懒加载,所有这儿需要进行一次load
            session.load(shop, shop.getId());
            session.refresh(shop);
            return shop;
        });
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
    public static List<SimpleShopCard> getShopsBySchoolId(School school) {
        return Hib.query(session ->
                session.createQuery("select new com.wuxiankeneng.jian.bean.card.SimpleShopCard(" +
                                "id,name,icon,isBusiness,description,deliveryDate,deliveryPrice,minimumPrice,sales)" +
                                " from Shop where school=:school order by sales desc ",
                        SimpleShopCard.class)
                        .setParameter("school", school)
                        .getResultList());
    }

    //用店铺id拿到简单的goods  推荐菜品
    public static List<SimpleGoodsCard> getRecommendsById(String shopId) {
        return Hib.query(session ->
                session.createQuery("select new com.wuxiankeneng.jian.bean.card.SimpleGoodsCard(icon,g_name)" +
                                " from Goods where recommendShopId=:shopId order by g_name asc ",
                        SimpleGoodsCard.class)
                        .setParameter("shopId", shopId)
                        .getResultList());

    }

    //搜索店铺
    public static List<SearchShopCard> searchShops(String name, School school) {
        return Hib.query(session ->

                session.createQuery("select new com.wuxiankeneng.jian.bean.card.SearchShopCard(id,name,description)" +
                        " from Shop where school=:school and name like ?1")
                        .setParameter("school", school)
                        .setParameter(1, "%" + name + "%")
                        .getResultList()
        );
    }

    public static List<SimpleShopCard> findShopsByType(School school, int type) {
        return Hib.query(session -> session.createQuery("select new com.wuxiankeneng.jian.bean.card.SimpleShopCard(" +
                "id,name,icon,isBusiness,description,deliveryDate,deliveryPrice,minimumPrice,sales) " +
                "from Shop where school=:school and shopType=:s_type order by sales desc ")
                .setParameter("school", school)
                .setParameter("s_type", type)
                .getResultList());
    }

    //TODO 懒加载出了问题,先自己进行查询店铺的商品集
    public static List<Goods> getAllGoods(Shop shop) {
        return Hib.query(session ->
                session.createQuery("from Goods where shop=:shop order by typeId asc ")
                        .setParameter("shop", shop)
                        .list());
    }
}
