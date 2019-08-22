package com.wuxiankeneng.jian.factory;

import com.wuxiankeneng.jian.bean.db.Goods;
import com.wuxiankeneng.jian.bean.db.School;
import com.wuxiankeneng.jian.bean.db.Shop;
import com.wuxiankeneng.jian.utils.Hib;

import java.util.List;

public class GoodsFactory {
    public static Goods findByNameAndShopId(Shop shop, String name) {
        return Hib.query(session -> (Goods) session
                .createQuery("from Goods where shop=:shop and g_name=:name")
                .setParameter("shop", shop)
                .setParameter("name", name)
                .uniqueResult());
    }

    public static Goods findById(String goodsId) {
        return Hib.query(session -> session.get(Goods.class, goodsId));
    }

    public static void deleteGoods(Goods goods) {
        Hib.queryOnly(session -> session.delete(goods));
    }


    //按日销量进行商品排行  查10个一次
    public static List<Goods> find(School school) {
        return Hib.query(session ->
                session.createQuery("select new com.wuxiankeneng.jian.bean.card.DailySalesGoodsCard(id,g_name,dailySales,icon,price,shop.name)" +
                        " from Goods " +
                        "where shop.school=:school order by dailySales desc ")
                        .setParameter("school", school)
                        .setMaxResults(10)
                        .getResultList());
    }


}
