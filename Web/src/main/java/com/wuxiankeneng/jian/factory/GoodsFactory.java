package com.wuxiankeneng.jian.factory;

import com.wuxiankeneng.jian.bean.db.Goods;
import com.wuxiankeneng.jian.bean.db.Shop;
import com.wuxiankeneng.jian.utils.Hib;

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
}
