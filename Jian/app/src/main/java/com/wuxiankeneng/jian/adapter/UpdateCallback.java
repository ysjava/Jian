package com.wuxiankeneng.jian.adapter;

import com.wuxiankeneng.factory.db.Goods;

public interface UpdateCallback {
    void addGoods(Goods goods);
    void removeGoods(Goods goods);
}
