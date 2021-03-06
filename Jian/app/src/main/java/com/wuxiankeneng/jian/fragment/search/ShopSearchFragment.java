package com.wuxiankeneng.jian.fragment.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.card.SearchShopCard;
import com.wuxiankeneng.factory.presenter.search.ShopSearchContract;
import com.wuxiankeneng.factory.presenter.search.ShopSearchPresenter;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.SearchActivity;
import com.wuxiankeneng.jian.activity.ShopActivity;
import com.wuxiankeneng.jian.fragment.BaseFragmentView;

import butterknife.BindView;

public class ShopSearchFragment extends BaseFragmentView<ShopSearchPresenter>
        implements SearchActivity.SearchFragment, ShopSearchContract.View {
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    RecyclerAdapter<SearchShopCard> adapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_shop;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter = new RecyclerAdapter<SearchShopCard>() {
            @Override
            protected int getItemViewType(int position, SearchShopCard shop) {
                return R.layout.item_search_shop;
            }

            @Override
            protected ViewHolder<SearchShopCard> onCreateViewHolder(View view, int viewType) {
                return new ShopSearchFragment.ViewHolder(view);
            }
        });

        adapter.setListener(new RecyclerAdapter.AdapterListenerImpl<SearchShopCard>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder viewHolder, SearchShopCard searchShopCard) {
                super.onItemClick(viewHolder, searchShopCard);
                ShopActivity.show(getContext(), searchShopCard.getId());
            }
        });
    }

    @Override
    public void search(String content) {
        if (TextUtils.isEmpty(content)) {
            Application.showToast("内容为空");
            return;
        }
        mPresenter.searchShop(content);

    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<SearchShopCard> {
        @BindView(R.id.txt_shop_name)
        TextView mShopName;
        @BindView(R.id.txt_shop_describe)
        TextView mShopDesc;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(SearchShopCard shop) {
            mShopDesc.setText(shop.getDesc());
            mShopName.setText(shop.getName());

        }
    }

    @Override
    public RecyclerAdapter<SearchShopCard> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }
}
