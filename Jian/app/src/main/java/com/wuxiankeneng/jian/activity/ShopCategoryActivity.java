package com.wuxiankeneng.jian.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.widget.EmptyView;
import com.wuxiankeneng.common.widget.RoundAngleImageView;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.card.SimpleShopCard;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.db.SimpleGoods;
import com.wuxiankeneng.factory.db.SimpleShop;
import com.wuxiankeneng.factory.presenter.shop.ShopCategoryContract;
import com.wuxiankeneng.factory.presenter.shop.ShopCategoryPresenter;
import com.wuxiankeneng.jian.R;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShopCategoryActivity extends BaseActivityView<ShopCategoryPresenter>
        implements ShopCategoryContract.View {
    @BindView(R.id.img_back)
    ImageView mBack;
    @BindView(R.id.edt_search_goods_type)
    EditText mEdtType;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.txt_type)
    TextView mTxtType;
    @BindView(R.id.empty_view)
    EmptyView<ShopCategoryActivity> mEmptyView;

    private RecyclerAdapter<SimpleShop> adapter;
    private int type;

    public static void show(Context context, int type) {
        Intent intent = new Intent(context, ShopCategoryActivity.class);
        intent.putExtra("TYPE", type);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        type = bundle.getInt("TYPE");
        return super.initArgs(bundle);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTxtType.setText(getTypeStr(type));
//        mEdtType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Application.showToast("店铺及商品的搜索,用类型分别,待做");
//            }
//        });

        //设置占位布局
        mEmptyView.setView(this);
        mEmptyView.bind(mEdtType, mRecycler);
        setPlaceHolderView(mEmptyView);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(adapter = new RecyclerAdapter<SimpleShop>() {
            @Override
            protected int getItemViewType(int position, SimpleShop shop) {
                return R.layout.item_catering_shop;
            }

            @Override
            protected ViewHolder<SimpleShop> onCreateViewHolder(View view, int viewType) {
                return new ShopCategoryActivity.ViewHolder(view);
            }
        });

        adapter.setListener(new RecyclerAdapter.AdapterListenerImpl<SimpleShop>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder viewHolder, SimpleShop shop) {
                super.onItemClick(viewHolder, shop);
                //跳转到商店详情页面,带id过去
                ShopActivity.show(ShopCategoryActivity.this, shop.getsId());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.load(type);
    }

    //把类型转换成对应字符串
    private String getTypeStr(int type) {
        switch (type) {
            case Shop.TYPE_HOT:
                return "今日热销";
            case Shop.TYPE_FAST_FOOD:
                return "快餐";
            case Shop.TYPE_DRINK:
                return "饮料甜点";
            case Shop.TYPE_STIR_FRY:
                return "炒菜";
            case Shop.TYPE_MULTIPLE:
                return "综合";
            default:
                return "类型错误";
        }
    }

    //网络异常 重新刷新
    @Override
    public void reFresh() {
        super.reFresh();
        mPresenter.load(type);
    }

    @Override
    public void showError(int str) {
        if (str != R.string.data_network_error)
            super.showError(str);
        else {
            if (mPlaceHolderView != null)
                mPlaceHolderView.triggerNetError();
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_shop_category;
    }

    @Override
    public RecyclerAdapter<SimpleShop> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void onAdapterDataChanged() {
        if (mPlaceHolderView != null)
            mPlaceHolderView.triggerOkOrEmpty(adapter.getItemCount() > 0);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<SimpleShop> {
        @BindView(R.id.portrait)
        RoundAngleImageView mPortrait;
        @BindView(R.id.txt_shop_name)
        TextView mShopName;
        @BindView(R.id.txt_shop_describe)
        TextView mShopDesc;
        @BindView(R.id.tj_food_img_1)
        CircleImageView mTjImg1;
        @BindView(R.id.tj_food_img_2)
        CircleImageView mTjImg2;
        @BindView(R.id.tj_food_img_3)
        CircleImageView mTjImg3;
        @BindView(R.id.txt_tj_food_desc_1)
        TextView mTjDesc1;
        @BindView(R.id.txt_tj_food_desc_2)
        TextView mTjDesc2;
        @BindView(R.id.txt_tj_food_desc_3)
        TextView mTjDesc3;
        @BindView(R.id.txt_tui)
        TextView mTui;
        @BindView(R.id.txt_jian)
        TextView mJian;

        @BindView(R.id.txt_month_sale)
        TextView mSale;
        @BindView(R.id.txt_delivery_price)
        TextView mDePrice;
        @BindView(R.id.txt_min_price)
        TextView mMinPrice;
        @BindView(R.id.txt_delivery_date)
        TextView mDate;

        @BindView(R.id.txt_shop_suspend)
        TextView mShopSuspend;
        @BindView(R.id.lay_item_shop)
        LinearLayout mLaySuspend;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(final SimpleShop shopCard) {
            Run.onUiAsync(new Action() {
                @SuppressLint("NewApi")
                @Override
                public void call() {
                    //是否营业的界面更新
                    if (!shopCard.isBusiness()) {
                        mShopSuspend.setVisibility(View.VISIBLE);
                        mLaySuspend.setForeground(getResources().getDrawable(R.drawable.bg_item_shop_suspend));
                        mShopName.setTextColor(getResources().getColor(R.color.grey_500));
                        mTui.setTextColor(getResources().getColor(R.color.grey_500));
                        mJian.setTextColor(getResources().getColor(R.color.grey_500));
                    } else {
                        mShopSuspend.setVisibility(View.GONE);
                        mLaySuspend.setForeground(null);
                        mShopName.setTextColor(getResources().getColor(R.color.grey_900));
                        mTui.setTextColor(getResources().getColor(R.color.colorAccent));
                        mJian.setTextColor(getResources().getColor(R.color.colorAccent));
                    }

                    mSale.setText(String.format("月售 : %s", shopCard.getSales()));
                    mDePrice.setText(String.format("配送 : %s", shopCard.getDeliveryPrice()));
                    mMinPrice.setText(String.format("起送 : %s", shopCard.getMinimumPrice()));
                    mDate.setText(String.format("%s 分钟", shopCard.getDeliveryDate()));

                    mShopName.setText(shopCard.getName());
                    mShopDesc.setText(shopCard.getDesc());

                    //into到店头像
                    Glide.with(ShopCategoryActivity.this)
                            .load(shopCard.getIcon())
                            .into(mPortrait);

                    List<SimpleGoods> goodsList = new ArrayList<>(shopCard.getSimpleGoods());
                    //into到推荐菜头像

                    switch (goodsList.size()) {
                        case 1:
                            Glide.with(ShopCategoryActivity.this)
                                    .load(goodsList.get(0).getIcon())
                                    .into(mTjImg1);
                            mTjDesc1.setText(goodsList.get(0).getName());
                            break;
                        case 2:
                            Glide.with(ShopCategoryActivity.this)
                                    .load(goodsList.get(0).getIcon())
                                    .into(mTjImg1);
                            mTjDesc1.setText(goodsList.get(0).getName());

                            Glide.with(ShopCategoryActivity.this)
                                    .load(goodsList.get(1).getIcon())
                                    .into(mTjImg2);
                            mTjDesc2.setText(goodsList.get(1).getName());
                        case 3:
                            Glide.with(ShopCategoryActivity.this)
                                    .load(goodsList.get(0).getIcon())
                                    .into(mTjImg1);
                            mTjDesc1.setText(goodsList.get(0).getName());

                            Glide.with(ShopCategoryActivity.this)
                                    .load(goodsList.get(1).getIcon())
                                    .into(mTjImg2);
                            mTjDesc2.setText(goodsList.get(1).getName());

                            Glide.with(ShopCategoryActivity.this)
                                    .load(goodsList.get(2).getIcon())
                                    .into(mTjImg3);
                            mTjDesc3.setText(goodsList.get(2).getName());
                        default:
                            break;

                    }

                }
            });
        }
    }
}
