package com.wuxiankeneng.jian.fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.wuxiankeneng.common.widget.RoundAngleImageView;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.card.Recommend;
import com.wuxiankeneng.factory.card.RecommendCard;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.db.SimpleGoods;
import com.wuxiankeneng.factory.db.SimpleShop;
import com.wuxiankeneng.factory.presenter.main.hone.HomeContact;
import com.wuxiankeneng.factory.presenter.main.hone.HomePresenter;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.SearchActivity;
import com.wuxiankeneng.jian.activity.ShopActivity;
import com.wuxiankeneng.jian.activity.ShopCategoryActivity;
import com.wuxiankeneng.jian.fragment.BaseFragmentView;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import org.w3c.dom.Text;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends BaseFragmentView<HomePresenter>
        implements HomeContact.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.edt_nav_search)
    EditText editText;
    @BindView(R.id.banner)
    BGABanner mBanner;
    //    @BindView(R.id.c_img_hot)
//    CircleImageView mCellHot;
    @BindView(R.id.c_img_fast_food)
    CircleImageView mCellFastFood;
    @BindView(R.id.c_img_fry_food)
    CircleImageView mCellFryFood;
    @BindView(R.id.c_img_drink)
    CircleImageView mCellDrink;
    @BindView(R.id.c_img_multiple_food)
    CircleImageView mCellMultipleFood;
    @BindView(R.id.recycler)
    RecyclerView mShopRecycler;

    List<View> list = new ArrayList<>();

    @BindView(R.id.lay_test2)
    LinearLayout lay_test2;
    @BindView(R.id.lay_test)
    LinearLayout lay_test;

//    @BindView(R.id.lay_hot)
//    LinearLayout mLayHot;
//    @BindView(R.id.lay_fast_food)
//    LinearLayout mLayHot;
//    @BindView(R.id.lay_stir_fry)
//    LinearLayout mLayHot;
//    @BindView(R.id.lay_drink)
//    LinearLayout mLayHot;
//    @BindView(R.id.lay_all)
//    LinearLayout mLayHot;

    @BindView(R.id.nested_scroll)
    NestedScrollView scrollView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private RecyclerAdapter<SimpleShop> adapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent);


        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= lay_test2.getTop()) {
                    lay_test.setVisibility(View.VISIBLE);
                } else
                    lay_test.setVisibility(View.GONE);
            }
        });

//        mCellHot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ShopActivity.show(getContext(), "test");
//            }
//        });


        mShopRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mShopRecycler.setAdapter(adapter = new RecyclerAdapter<SimpleShop>() {

            @Override
            protected int getItemViewType(int position, SimpleShop shop) {
                return R.layout.item_catering_shop;
            }

            @Override
            protected ViewHolder<SimpleShop> onCreateViewHolder(View view, int viewType) {
                return new HomeFragment.ViewHolder(view);
            }
        });
        adapter.setListener(new RecyclerAdapter.AdapterListenerImpl<SimpleShop>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder viewHolder, SimpleShop shop) {
                //跳转到商店详情页面,带id过去
                Intent intent = new Intent(getContext(), ShopActivity.class);
                intent.putExtra("shopId", shop.getsId());
                startActivity(intent);
            }
        });

        mBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
                Recommend recommend = (Recommend) model;
                assert recommend != null;
                if (recommend.getType() == Recommend.TYPE_ID) {
                    Toast.makeText(banner.getContext(), "我是类型 : " + recommend.getType(), Toast.LENGTH_SHORT).show();
                    //是id就跳店铺
                    Intent intent = new Intent(banner.getContext(), ShopActivity.class);
                    intent.putExtra("shopId", recommend.getShopIdOrAdvertUrl());
                    startActivity(intent);
                } else if (recommend.getType() == Recommend.TYPE_URL) {
                    Toast.makeText(banner.getContext(), "我是类型 : " + recommend.getType(), Toast.LENGTH_SHORT).show();

                    //是广告网址就跳浏览器去
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(recommend.getShopIdOrAdvertUrl()));
                    startActivity(intent);
                }
            }
        });

        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                ImageView imageView = itemView.findViewById(R.id.iv_pager);
                Recommend recommend = (Recommend) model;
                assert recommend != null;
                Glide.with(Objects.requireNonNull(getContext()))
                        .load(recommend.getImgUrl())
                        .dontAnimate()
                        .centerCrop()
                        .into(imageView);

            }
        });
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        //更新信息
        mPresenter.loadRecommend();
        mPresenter.loadSimpleShops();
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
        protected void onBind(final SimpleShop shop) {
            Run.onUiAsync(new Action() {
                @SuppressLint("NewApi")
                @Override
                public void call() {
                    //是否营业的界面更新
                    if (!shop.isBusiness()) {
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

                    mSale.setText(String.format("月售 : %s", shop.getSales()));
                    mDePrice.setText(String.format("配送 : %s", shop.getDeliveryPrice()));
                    mMinPrice.setText(String.format("起送 : %s", shop.getMinimumPrice()));
                    mDate.setText(String.format("%s 分钟", shop.getDeliveryDate()));

                    mShopName.setText(shop.getName());
                    mShopDesc.setText(shop.getDesc());

                    //into到店头像
                    Glide.with(Objects.requireNonNull(getContext()))
                            .load(shop.getIcon())
                            .into(mPortrait);

                    List<SimpleGoods> goodsList = new ArrayList<>(shop.getSimpleGoods());
                    //into到推荐菜头像
                    Glide.with(getContext())
                            .load(goodsList.get(0).getIcon())
                            .into(mTjImg1);
                    mTjDesc1.setText(goodsList.get(0).getName());

                    Glide.with(getContext())
                            .load(goodsList.get(1).getIcon())
                            .into(mTjImg2);
                    mTjDesc2.setText(goodsList.get(1).getName());

                    Glide.with(getContext())
                            .load(goodsList.get(2).getIcon())
                            .into(mTjImg3);
                    mTjDesc3.setText(goodsList.get(2).getName());

                }
            });
        }
    }

    @Override
    protected void initData() {
        super.initData();
        //加载推荐数据
        mPresenter.loadRecommend();
        //加载商店列表
        mPresenter.loadSimpleShops();

    }

    @OnClick(R.id.edt_nav_search)
    public void searchShop() {
        SearchActivity.show(getContext(), SearchActivity.TYPE_SHOP, null);
    }

    @OnClick(R.id.lay_hot)
    public void searchShopByTypeHot() {
        ShopCategoryActivity.show(getContext(), Shop.TYPE_HOT);
    }

    @OnClick(R.id.lay_fast_food)
    public void searchShopByTypeFast() {
        ShopCategoryActivity.show(getContext(), Shop.TYPE_FAST_FOOD);
    }

    @OnClick(R.id.lay_stir_fry)
    public void searchShopByTypeFry() {
        ShopCategoryActivity.show(getContext(), Shop.TYPE_STIR_FRY);
    }

    @OnClick(R.id.lay_drink)
    public void searchShopByTypeDrink() {
        ShopCategoryActivity.show(getContext(), Shop.TYPE_DRINK);
    }

    @OnClick(R.id.lay_multiple)
    public void searchShopByTypeMultiple() {
        ShopCategoryActivity.show(getContext(), Shop.TYPE_MULTIPLE);
    }


    @SuppressLint("InflateParams")
    @Override
    public void showRecommend(List<Recommend> recommends) {
        if (recommends.size() == 0)
            return;
        list.clear();
        for (int i = 0; i < recommends.size(); i++) {
            list.add(getLayoutInflater().inflate(R.layout.page_1, null, false));
        }
        mBanner.setData(list, recommends, null);
    }


    @Override
    public RecyclerAdapter<SimpleShop> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void onAdapterDataChanged() {
        if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }
}
