package com.wuxiankeneng.jian.fragment.main;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.card.GoodsCard;
import com.wuxiankeneng.factory.db.Order;
import com.wuxiankeneng.factory.presenter.main.order.MainOrderContract;
import com.wuxiankeneng.factory.presenter.main.order.MainOrderPresenter;
import com.wuxiankeneng.factory.tools.Arithmetic;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.OrderActivity;
import com.wuxiankeneng.jian.fragment.BaseFragmentView;

import net.qiujuer.genius.ui.widget.Loading;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;

public class OrderFragment extends BaseFragmentView<MainOrderPresenter>
        implements MainOrderContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.lay_swipe)
    SwipeRefreshLayout mLaySwipe;
    @BindView(R.id.loading)
    Loading mLoading;
    private RecyclerAdapter<Order> adapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mLaySwipe.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mLaySwipe.setOnRefreshListener(this);

        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(adapter = new RecyclerAdapter<Order>() {

            @Override
            protected int getItemViewType(int position, Order order) {
                return R.layout.item_order_goods_main;
            }

            @Override
            protected ViewHolder<Order> onCreateViewHolder(View view, int viewType) {
                return new OrderFragment.ViewHolder(view);
            }
        });

        adapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Order>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder viewHolder, Order order) {
                super.onItemClick(viewHolder, order);
                //跳到订单详细界面
                //试试在这儿先进行查询到order ,然后在跳转过去//先弹个加载框
                mLoading.setForegroundColor(getResources().getColor(R.color.colorAccent));
                mLoading.start();
                mLoading.setVisibility(View.VISIBLE);
                mPresenter.preLoadingOrder(order.getoId());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public RecyclerAdapter<Order> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void onAdapterDataChanged() {
        if (mLaySwipe != null && mLaySwipe.isRefreshing()) {
            mLaySwipe.setRefreshing(false);
        }
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        if (mLoading.isRunning()){
            mLoading.stop();
            mLoading.setVisibility(View.GONE);

        }

        if (mLaySwipe != null && mLaySwipe.isRefreshing()) {
            mLaySwipe.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.start();
    }

    //当点击订单item跳转到详情时,先进行预加载,加载完成后到这儿就跳转到详情界面
    @Override
    public void preLoadingResult(Order order) {
        if (mLoading.isRunning()){
            mLoading.stop();
            mLoading.setVisibility(View.GONE);
        }
        OrderActivity.show(getContext(), order);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Order> {
        @BindView(R.id.img_icon)
        ImageView icon;
        @BindView(R.id.txt_shop_name)
        TextView name;
        @BindView(R.id.txt_order_state)
        TextView state;
        @BindView(R.id.txt_first_goods_name)
        TextView mFirstGoodsName;
        @BindView(R.id.txt_price_count)
        TextView count;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Order order) {
            Glide.with(getActivity())
                    .load(order.getShopIcon())
                    .into(icon);

            name.setText(order.getShopName());
            state.setText(getState(order.getState()));
            mFirstGoodsName.setText(getFirstGoodsName(order.getEntity()));
            count.setText(getCountPrice(order));
        }

        private String getCountPrice(Order order) {
            Type type = new TypeToken<List<GoodsCard>>() {
            }.getType();
            List<GoodsCard> cardList = Factory.getGson().fromJson(order.getEntity(), type);
            double price = 0;
            for (GoodsCard goodsCard : cardList) {
                price = Arithmetic.add(price, Arithmetic.mul(Double.parseDouble(goodsCard.getPrice()), goodsCard.getCount()));
            }
            price = Arithmetic.add(price, Double.parseDouble(order.getDeliveryPrice()));
            price = Arithmetic.add(price, Double.parseDouble(getPackingPrice(cardList)));

            return String.valueOf(price);
        }

        //包装费
        private String getPackingPrice(List<GoodsCard> cardList) {
            double count = 0;
            for (GoodsCard goodsCard : cardList) {
                count = Arithmetic.add(count, Arithmetic.mul(Double.parseDouble(goodsCard.getPackingPrice() == null ? "0" : goodsCard.getPackingPrice()),
                        goodsCard.getCount()));
            }
            return String.valueOf(count);
        }

        //返回的是菜品集合的第一个名字和全部数量的字符串
        private String getFirstGoodsName(String entity) {
            //解析entity  entity就是打包的商品信息集合
            Type type = new TypeToken<List<GoodsCard>>() {
            }.getType();
            List<GoodsCard> cardList = Factory.getGson().fromJson(entity, type);
            return String.format("%s  等%s件商品", cardList.get(0).getName(), String.valueOf(cardList.size()));
        }

        private String getState(int state) {
            if (state == Order.STATE_PROCESSING)
                return "订单待处理";
            if (state == Order.STATE_ACCEPT)
                return "商家已接受";
            if (state == Order.STATE_DONE)
                return "订单已完成";

            return "状态获取失败";
        }
    }
}
