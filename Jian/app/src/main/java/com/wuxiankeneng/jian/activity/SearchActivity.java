package com.wuxiankeneng.jian.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuxiankeneng.common.app.BaseActivity;
import com.wuxiankeneng.common.app.BaseFragment;


import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.fragment.search.GoodsSearchFragment;
import com.wuxiankeneng.jian.fragment.search.ShopSearchFragment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {

    private static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final int TYPE_SHOP = 0;//搜索店铺
    public static final int TYPE_GOODS_IN_SHOP = 1;//在店铺中搜索商品

    private int type;
    private String shopId;
    private SearchFragment mSearchFragment;
    @BindView(R.id.edt_search)
    EditText mSearch;
    @BindView(R.id.img_back)
    ImageView mBack;
    @BindView(R.id.txt_search)
    TextView mBtnSearch;
    private HashMap<String, Goods> map;

    /**
     * 主界面搜索时调用  搜索商店
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(EXTRA_TYPE, TYPE_SHOP);
        context.startActivity(intent);
    }

    /**
     * 在商店中搜索时调用
     *
     * @param context  上下文
     * @param shopId   店铺id
     * @param goodsMap 在店铺界面搜索才需要的参数: 已选中的菜品
     */
    public static void show(Context context, String shopId, HashMap<String, Goods> goodsMap) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(EXTRA_TYPE, TYPE_GOODS_IN_SHOP);
        intent.putExtra("SHOP_ID", shopId);
        intent.putExtra("GOODS_LIST", goodsMap);

        ((ShopActivity) context).startActivityForResult(intent, 1);
    }


    public HashMap<String, Goods> getMap() {
        return map;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected boolean initArgs(Bundle bundle) {
        this.type = bundle.getInt(EXTRA_TYPE);
        this.shopId = bundle.getString("SHOP_ID");
        if (type == TYPE_GOODS_IN_SHOP) {
            map = (HashMap<String, Goods>) getIntent().getSerializableExtra("GOODS_LIST");
        }

        return type == TYPE_GOODS_IN_SHOP || type == TYPE_SHOP;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == TYPE_GOODS_IN_SHOP) {
                    Intent intent = new Intent();
                    intent.putExtra("GOODS_LIST", map);
                    setResult(RESULT_OK, intent);
                }
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
        if (type == TYPE_GOODS_IN_SHOP) {
            mSearch.setHint(R.string.txt_hint_goods_name);
            GoodsSearchFragment goodsSearchFragment = new GoodsSearchFragment();
            fragment = goodsSearchFragment;
            mSearchFragment = goodsSearchFragment;
        } else {
            mSearch.setHint(R.string.txt_nav_edt_hint);
            ShopSearchFragment shopSearchFragment = new ShopSearchFragment();
            fragment = shopSearchFragment;
            mSearchFragment = shopSearchFragment;
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container, fragment)
                .commit();
    }

    public interface SearchFragment {
        void search(String content);
    }

    public String getShopId() {
        return shopId;
    }
}
