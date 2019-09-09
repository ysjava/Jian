package com.wuxiankeneng.jian.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.card.GoodsCard;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Order;
import com.wuxiankeneng.factory.presenter.order.OrderContract;
import com.wuxiankeneng.factory.presenter.order.OrderPresenter;
import com.wuxiankeneng.factory.tools.Arithmetic;
import com.wuxiankeneng.jian.R;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;

/**
 * Created by White paper on 2019/9/5
 * Describe : 订单的信息的界面
 */
public class OrderActivity extends BaseActivityView<OrderPresenter> implements OrderContract.View {
    private Order order;

    @BindView(R.id.txt_state)
    TextView mState;
    @BindView(R.id.txt_shop_name)
    TextView mShopName;
    @BindView(R.id.txt_address)
    TextView mAddress;
    @BindView(R.id.txt_name)
    TextView mName;
    @BindView(R.id.txt_phone)
    TextView mPhone;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.txt_packing_price)
    TextView mPackingPrice;//包装费
    @BindView(R.id.txt_delivery_price)
    TextView mDelPrice;
    @BindView(R.id.txt_price_count)
    TextView mPriceCount;
    @BindView(R.id.txt_remarks)
    TextView mRemarks;//备注
    @BindView(R.id.txt_order_number)
    TextView mOrderNumber;
    @BindView(R.id.lay_shopping_cart)
    RelativeLayout mLayCart;
    @BindView(R.id.txt_go_pay)
    TextView mGoPay;

    @BindView(R.id.img_right)
    ImageView mRight;
    private RecyclerAdapter<GoodsCard> adapter;
    public static void show(Context context, Order order) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("ORDER", order);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        this.order = (Order) bundle.getSerializable("ORDER");
        return order != null;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_order_commit;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mLayCart.setVisibility(View.GONE);
        mRight.setVisibility(View.GONE);

        //设置状态
        mState.setText(getState(order.getState()));
        mAddress.setText(order.getAddress().getAddress());
        mName.setText(order.getAddress().getName());
        mPhone.setText(order.getAddress().getPhone());
        mShopName.setText(order.getShopName());
        mShopName.setText(order.getShopName());

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        List<GoodsCard> cardList = getGoodsCardList();


        mRecycler.setAdapter(adapter = new RecyclerAdapter<GoodsCard>(cardList,null){

            @Override
            protected int getItemViewType(int position, GoodsCard goodsCard) {
                return R.layout.item_order_goods;
            }

            @Override
            protected ViewHolder<GoodsCard> onCreateViewHolder(View view, int viewType) {
                return new OrderActivity.ViewHolder(view);
            }
        });

        //配送费
        mDelPrice.setText(order.getDeliveryPrice());
        //订单号
        mOrderNumber.setText(String.format("订单号: %s", order.getoId()));
        //总价
        mPriceCount.setText(getPriceCount());
        //计算包装费
        mPackingPrice.setText(getPackingPrice());
    }

    //计算包装费
    private String getPackingPrice() {
        List<GoodsCard> cardList = getGoodsCardList();
        double count = 0;
        for (GoodsCard goodsCard : cardList) {
            count = Arithmetic.add(count, Arithmetic.mul(Double.parseDouble(goodsCard.getPackingPrice() == null ? "0" : goodsCard.getPackingPrice()),
                    goodsCard.getCount()));
        }
        return String.valueOf(count);
    }

    private List<GoodsCard> getGoodsCardList() {
        Type type = new TypeToken<List<GoodsCard>>() {
        }.getType();
        return Factory.getGson().fromJson(order.getEntity(), type);
    }

    //计算总价格
    private String getPriceCount() {
        List<GoodsCard> cardList = getGoodsCardList();
        double count = 0;
        for (GoodsCard goodsCard : cardList) {
            count = Arithmetic.add(count, Arithmetic.mul(Double.parseDouble(goodsCard.getPrice()), goodsCard.getCount()));
        }
        count = Arithmetic.add(count, Double.parseDouble(order.getDeliveryPrice()));
        count = Arithmetic.add(count, Double.parseDouble(getPackingPrice()));
        return String.valueOf(count);
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

    @Override
    public RecyclerAdapter<GoodsCard> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<GoodsCard>{
        @BindView(R.id.imageView)
        ImageView mImageView;
        @BindView(R.id.txt_goods_name)
        TextView mName;
        @BindView(R.id.txt_price)
        TextView mPrice;
        @BindView(R.id.txt_number)
        TextView mNumber;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(GoodsCard card) {
            Glide.with(OrderActivity.this)
                    .load(card.getIcon())
                    .dontAnimate()
                    .into(mImageView);
            mName.setText(card.getName());
            mPrice.setText(card.getPrice());
            mNumber.setText(String.format("x%s", String.valueOf(card.getCount())));

        }
    }
}
