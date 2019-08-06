package com.wuxiankeneng.jian.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuxiankeneng.common.app.BaseActivity;
import com.wuxiankeneng.common.app.BaseFragment;

import com.wuxiankeneng.common.widget.RoundAngleImageView;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.db.Shop;

import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.fragment.search.GoodsSearchFragment;
import com.wuxiankeneng.jian.fragment.search.ShopSearchFragment;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;


public class SearchActivity extends BaseActivity {

    private static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final int TYPE_SHOP = 0;//搜索店铺
    public static final int TYPE_GOODS_IN_SHOP = 1;//在店铺中搜索商品

    private int type;
    private SearchFragment mSearchFragment;
    @BindView(R.id.edt_search)
    EditText mSearch;
    @BindView(R.id.img_back)
    ImageView mBack;
    @BindView(R.id.txt_search)
    TextView mBtnSearch;

    /**
     * @param context 上下文
     * @param type    搜索的类型
     */
    public static void show(Context context, int type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        this.type = bundle.getInt(EXTRA_TYPE);
        return type == TYPE_GOODS_IN_SHOP || type == TYPE_SHOP;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchFragment.search(mSearch.getText().toString().trim());
            }
        });

        //这儿是缓存一个而不是用全局的是因为就优化而言,使用局部变量快得多
        BaseFragment fragment;
        //显示对应的fragment
        if (type == TYPE_SHOP) {
            mSearch.setHint(R.string.txt_nav_edt_hint);
            GoodsSearchFragment goodsSearchFragment = new GoodsSearchFragment();
            fragment = goodsSearchFragment;
            mSearchFragment = goodsSearchFragment;
        } else {
            mSearch.setHint(R.string.txt_hint_goods_name);
            ShopSearchFragment shopSearchFragment = new ShopSearchFragment();
            fragment = shopSearchFragment;
            mSearchFragment = shopSearchFragment;
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container, fragment)
                .commit();
    }

    //    @BindView(R.id.img_back)
//    ImageView mBack;
//    @BindView(R.id.edt_search)
//    EditText mSearch;
//    @BindView(R.id.txt_search)
//    TextView mBtnSearch;
//    @BindView(R.id.recycler)
//    RecyclerView mRecycler;
//
//    private RecyclerAdapter<Shop> adapter;

//    @Override
//    public RecyclerAdapter getRecyclerAdapter() {
//        return adapter;
//    }
//
//    @Override
//    public void onAdapterDataChanged() {
//
//    }

//    @Override
//    protected void initInject() {
//        getActivityComponent().inject(this);
//    }

    //    @Override
//    protected void initWidget() {
//        super.initWidget();
//        mRecycler.setLayoutManager(new LinearLayoutManager(this));
//        mRecycler.setAdapter(adapter = new RecyclerAdapter<Shop>() {
//            @Override
//            protected int getItemViewType(int position, Shop shop) {
//                return R.layout.item_search_shop;
//            }
//
//            @Override
//            protected ViewHolder<Shop> onCreateViewHolder(View view, int viewType) {
//                return new SearchActivity.ViewHolder(view);
//            }
//        });
//    }
//

//
//    @OnClick(R.id.img_back)
//    public void backClick() {
//        finish();
//    }
//
//    @OnClick(R.id.txt_search)
//    public void btnSearchClick() {
//        String searchString = mSearch.getText().toString().trim();
////        mPresenter.searchShop(searchString);
//    }
//
//    class ViewHolder extends RecyclerAdapter.ViewHolder<Shop> {
//        @BindView(R.id.portrait)
//        RoundAngleImageView mPortrait;
//        @BindView(R.id.txt_shop_name)
//        TextView mShopName;
//        @BindView(R.id.txt_shop_describe)
//        TextView mShopDesc;
//
//        ViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//
//        @Override
//        protected void onBind(Shop shop) {
//            Glide.with(SearchActivity.this)
//                    .load(shop.getImg())
//                    .dontAnimate()
//                    .into(mPortrait);
//
//            mShopName.setText(shop.getName());
//            mShopDesc.setText(shop.getDesc());
//        }
//    }

    public interface SearchFragment {
        void search(String content);
    }
}
