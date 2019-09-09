package com.wuxiankeneng.jian.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.card.AddressCard;
import com.wuxiankeneng.factory.card.GoodsCard;
import com.wuxiankeneng.factory.card.OrderCard;
import com.wuxiankeneng.factory.model.order.CreateOrderModel;
import com.wuxiankeneng.factory.presenter.order.OrderCommitContract;
import com.wuxiankeneng.factory.presenter.order.OrderCommitPresenter;
import com.wuxiankeneng.factory.tools.Arithmetic;
import com.wuxiankeneng.jian.App;
import com.wuxiankeneng.jian.MainActivity;
import com.wuxiankeneng.jian.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by White paper on 2019/9/2
 * Describe : 订单提交界面
 */
public class OrderCommitActivity extends BaseActivityView<OrderCommitPresenter>
        implements OrderCommitContract.View {
    private CreateOrderModel model;
    private String shopName;
    private String deliveryPrice;

    //    @BindView(R.id.txt_state)
//    TextView mState;
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
    @BindView(R.id.txt_price_count_cart)
    TextView mPriceCountCart;


    //deliPrice 是配送价格  deli == delivery
    public static void show(Context context, CreateOrderModel model, String shopName, String deliPrice) {
        Intent intent = new Intent(context, OrderCommitActivity.class);
        intent.putExtra("MODEL", model);
        intent.putExtra("NAME", shopName);
        intent.putExtra("PRICE", deliPrice);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        CreateOrderModel model = (CreateOrderModel) bundle.getSerializable("MODEL");
        this.model = model;
        this.shopName = bundle.getString("NAME", null);
        this.deliveryPrice = bundle.getString("PRICE");
        return model != null && shopName != null;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //地址  地址暂时没有记忆,所以始终为空
        if (model.getAddress() != null) {
            mAddress.setText(model.getAddress().getAddress());
            mName.setText(model.getAddress().getName());
            mPhone.setText(model.getAddress().getPhone());
        }


        //店名
        mShopName.setText(shopName);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(new RecyclerAdapter<GoodsCard>(model.getGoodsCards(), null) {
            @Override
            protected int getItemViewType(int position, GoodsCard goodsCard) {
                return R.layout.item_order_goods;
            }

            @Override
            protected ViewHolder<GoodsCard> onCreateViewHolder(View view, int viewType) {
                return new OrderCommitActivity.ViewHolder(view);
            }
        });
        //配送费
        mDelPrice.setText(deliveryPrice);
        //订单号
        mOrderNumber.setText(String.format("订单号: %s", model.getId()));
        //总价
        mPriceCount.setText(getPriceCount());
        //购物车的总价格显示
        mPriceCountCart.setText(String.format("合计 : ￥ %s", getPriceCount()));
        //计算包装费
        mPackingPrice.setText(getPackingPrice());
    }

    //计算后的包装费
    private String getPackingPrice() {
        double count = 0;
        for (GoodsCard goodsCard : model.getGoodsCards()) {
            count = Arithmetic.add(count, Arithmetic.mul(Double.parseDouble(goodsCard.getPackingPrice() == null ? "0" : goodsCard.getPackingPrice()),
                    goodsCard.getCount()));
        }
        return String.valueOf(count);
    }

    //计算后得到总价格,用字符串类型显示,浮点有精度丢失
    private String getPriceCount() {
        double count = 0;
        for (GoodsCard goodsCard : model.getGoodsCards()) {
            count = Arithmetic.add(count, Arithmetic.mul(Double.parseDouble(goodsCard.getPrice()), goodsCard.getCount()));
        }
        count = Arithmetic.add(count, Double.parseDouble(deliveryPrice));
        count = Arithmetic.add(count, Double.parseDouble(getPackingPrice()));
        return String.valueOf(count);
    }

    //地址选择
    @OnClick(R.id.lay_address)
    public void choice() {
        // 跳转地址选择界面  选择完成应该返回一个addressCard到onActivityResult
        AddressActivity.show(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //地址选择完成 并设置
                AddressCard card = (AddressCard) (data != null ? data.getSerializableExtra("ADDRESS") : null);
                if (card == null) {
                    Application.showToast("地址选择出错,请重试");
                    return;
                }
                model.setAddress(card);

            }
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //地址选择完成后进行设置
        if (model.getAddress() != null) {
            mAddress.setText(model.getAddress().getAddress());
            mName.setText(model.getAddress().getName());
            mPhone.setText(model.getAddress().getPhone());
        }
    }

    //备注
    @OnClick(R.id.lay_remarks)
    public void remarks() {
        //TODO 跳转备注输入界面;
    }

    //提交订单并支付(这儿先不做支付功能)
    @OnClick(R.id.txt_go_pay)
    public void pay() {
        //提交订单 成功后跳转一个支付成功界面
        if (model.getAddress() == null) {
            Application.showToast("请先选择地址");
            return;
        }
        mPresenter.commit(model);
    }


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_order_commit;
    }

    //成功提交了  跳转到订单界面
    @Override
    public void success(OrderCard orderCard) {
        MainActivity.show(this);
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<GoodsCard> {
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
        protected void onBind(GoodsCard goodsCard) {
            Glide.with(OrderCommitActivity.this)
                    .load(goodsCard.getIcon())
                    .dontAnimate()
                    .into(mImageView);
            mName.setText(goodsCard.getName());
            mPrice.setText(goodsCard.getPrice());
            mNumber.setText(String.format("x%s", String.valueOf(goodsCard.getCount())));

        }
    }
}
