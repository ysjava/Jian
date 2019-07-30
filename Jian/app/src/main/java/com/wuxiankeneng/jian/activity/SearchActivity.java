package com.wuxiankeneng.jian.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuxiankeneng.common.widget.RoundAngleImageView;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.presenter.search.SearchContract;
import com.wuxiankeneng.factory.presenter.search.SearchPresenter;
import com.wuxiankeneng.jian.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;


public class SearchActivity extends BaseActivityView<SearchPresenter>
        implements SearchContract.View {
    @BindView(R.id.img_back)
    ImageView mBack;
    @BindView(R.id.edt_search)
    EditText mSearch;
    @BindView(R.id.txt_search)
    TextView mBtnSearch;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private RecyclerAdapter<Shop> adapter;

    @Override
    public RecyclerAdapter getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(adapter = new RecyclerAdapter<Shop>() {
            @Override
            protected int getItemViewType(int position, Shop shop) {
                return R.layout.item_search_shop;
            }

            @Override
            protected ViewHolder<Shop> onCreateViewHolder(View view, int viewType) {
                return new SearchActivity.ViewHolder(view);
            }
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }

    @OnClick(R.id.img_back)
    public void backClick() {
        finish();
    }

    @OnClick(R.id.txt_search)
    public void btnSearchClick() {
        String searchString = mSearch.getText().toString().trim();
        mPresenter.searchShop(searchString);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Shop> {
        @BindView(R.id.portrait)
        RoundAngleImageView mPortrait;
        @BindView(R.id.txt_shop_name)
        TextView mShopName;
        @BindView(R.id.txt_shop_describe)
        TextView mShopDesc;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Shop shop) {
            Glide.with(SearchActivity.this)
                    .load(shop.getImg())
                    .dontAnimate()
                    .into(mPortrait);

            mShopName.setText(shop.getName());
            mShopDesc.setText(shop.getDesc());
        }
    }
}
