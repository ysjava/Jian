package com.wuxiankeneng.factory.card;

import com.wuxiankeneng.factory.db.Goods;

public class GoodsCard {

    public Goods build() {
        Goods goods = new Goods();
        return goods;
    }
}
