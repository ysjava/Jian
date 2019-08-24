package com.wuxiankeneng.factory.presenter.search;

import android.util.Log;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BasePresenter;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.helper.SearchHelper;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class GoodsSearchPresenter extends BaseRecyclerPresenter<Goods, GoodsSearchContract.View>
        implements GoodsSearchContract.Presenter, DataSource.Callback<List<Goods>> {
    @Inject
    public GoodsSearchPresenter() {
    }

    @Override
    public void searchGoods(String goodsName, String shopId) {

        SearchHelper.searchGoods(goodsName, shopId, this);
    }


    @Override
    public void onDataLoaded(final List<Goods> goodsList) {
        //拿到选中商品的列表
        Map<String, Goods> selectedGoodsList = getView().getSelectedGoodsList();
        //保存 转换后的 以选中商品数据
        final ArrayList<Goods> list = new ArrayList<>();
        //把搜索回来的数据和已经选中的数据进行对比,如果id有相同的,就把选中的商品的数量赋值到对应结果上
        for (Goods goods : goodsList) {
            for (Goods value : selectedGoodsList.values()) {
                if (goods.getsId().equals(value.getsId())) {
                    //id相同,表示搜索回来的商品和已经选中的商品有相同的,那么就把选中的商品的数量赋值到结果上去
                    goods.setCount(value.getCount());

                    list.add(goods);
                }
            }
        }
        //覆盖原数据,因为查询回来的和原来的不是同一对象,要想操作就必须是同一对象
        for (Goods goods : list) {
            if (selectedGoodsList.get(goods.getsId()) != null || selectedGoodsList.containsKey(goods.getsId())) {
                selectedGoodsList.put(goods.getsId(), goods);
            }
        }

        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                refreshData(goodsList);
                //当搜索的数据和已选中的数据处理完成后的购物车刷新
                getView().updateShopping();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                getView().showError(strRes);
            }
        });
    }
}
