package com.wuxiankeneng.jian.fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.wuxiankeneng.common.widget.RoundAngleImageView;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.card.Recommend;
import com.wuxiankeneng.factory.card.RecommendCard;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.presenter.main.hone.HomeContact;
import com.wuxiankeneng.factory.presenter.main.hone.HomePresenter;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.SearchActivity;
import com.wuxiankeneng.jian.activity.ShopActivity;
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
        implements HomeContact.View {

    @BindView(R.id.edt_nav_search)
    EditText editText;
    @BindView(R.id.banner)
    BGABanner mBanner;
    @BindView(R.id.c_img_hot)
    CircleImageView mCellHot;
    @BindView(R.id.c_img_fast_food)
    CircleImageView mCellFastFood;
    @BindView(R.id.c_img_fry_food)
    CircleImageView mCellFryFood;
    @BindView(R.id.c_img_drink)
    CircleImageView mCellDrink;
    @BindView(R.id.c_img_all_food)
    CircleImageView mCellAllFood;
    @BindView(R.id.recycler)
    RecyclerView mShopRecycler;
    List<View> list = new ArrayList<>();

    private RecyclerAdapter<Shop> adapter;

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

        mCellHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopActivity.show(getContext(),"test");
            }
        });

        mShopRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mShopRecycler.setAdapter(adapter = new RecyclerAdapter<Shop>() {

            @Override
            protected int getItemViewType(int position, Shop shop) {
                return R.layout.item_catering_shop;
            }

            @Override
            protected ViewHolder<Shop> onCreateViewHolder(View view, int viewType) {
                return new HomeFragment.ViewHolder(view);
            }
        });
        adapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Shop>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder viewHolder, Shop shop) {
                //跳转到商店详情页面,带id过去
                Intent intent = new Intent(getContext(), ShopActivity.class);
                intent.putExtra("id", shop.getsId());
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
//                    Intent intent = new Intent(banner.getContext(), ShopActivity.class);
//                    intent.putExtra("id", recommend.getUrlOrId());
//                    startActivity(intent);
                } else if (recommend.getType() == Recommend.TYPE_URL) {
                    Toast.makeText(banner.getContext(), "我是类型 : " + recommend.getType(), Toast.LENGTH_SHORT).show();

                    //是广告网址就跳webView去
//                    Intent intent = new Intent(banner.getContext(), ShopActivity.class);
//                    intent.putExtra("id", recommend.getUrlOrId());
//                    startActivity(intent);
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
                        .load(recommend.getImgPath())
                        .dontAnimate()
                        .centerCrop()
                        .into(imageView);

            }
        });
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<Shop> {
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


        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(final Shop shop) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    mShopName.setText(shop.getName());
                    mShopDesc.setText(shop.getDesc());

                    //into到店头像
                    Glide.with(Objects.requireNonNull(getContext()))
                            .load(shop.getImg())
                            .into(mPortrait);

                    List<Goods> goodsList = shop.getRecommendGoods();
                    //into到推荐菜头像
                    Glide.with(getContext())
                            .load(goodsList.get(0).getImg())
                            .into(mTjImg1);
                    mTjDesc1.setText(goodsList.get(0).getName());

                    Glide.with(getContext())
                            .load(goodsList.get(1).getImg())
                            .into(mTjImg2);
                    mTjDesc1.setText(goodsList.get(1).getName());

                    Glide.with(getContext())
                            .load(goodsList.get(2).getImg())
                            .into(mTjImg3);
                    mTjDesc1.setText(goodsList.get(2).getName());

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
        mPresenter.loadShops();


    }

    @OnClick(R.id.edt_nav_search)
    public void testClick() {
        SearchActivity.show(getContext(),SearchActivity.TYPE_SHOP);
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
    public RecyclerAdapter<Shop> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }
}
