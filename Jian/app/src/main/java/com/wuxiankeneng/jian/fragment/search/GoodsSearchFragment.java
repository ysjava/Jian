package com.wuxiankeneng.jian.fragment.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.app.BaseFragment;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.presenter.search.GoodsSearchContract;
import com.wuxiankeneng.factory.presenter.search.GoodsSearchPresenter;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.SearchActivity;
import com.wuxiankeneng.jian.fragment.BaseFragmentView;

import butterknife.BindView;

//TODO 在商店搜索商品先暂时不写
public class GoodsSearchFragment extends BaseFragmentView<GoodsSearchPresenter>
        implements SearchActivity.SearchFragment, GoodsSearchContract.View {
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    RecyclerAdapter<Goods> adapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_goods;
    }

    @Override
    public void search(String content) {
        if (TextUtils.isEmpty(content)) {
            Application.showToast("内容为空");
            return;
        }
        mPresenter.searchGoods(content);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter = new RecyclerAdapter<Goods>() {
            @Override
            protected int getItemViewType(int position, Goods goods) {
                return R.layout.item_search_goods;
            }

            @Override
            protected ViewHolder<Goods> onCreateViewHolder(View view, int viewType) {
                return new GoodsSearchFragment.ViewHolder(view);
            }
        });
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Goods> {

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Goods goods) {

        }
    }

    @Override
    public RecyclerAdapter<Goods> getRecyclerAdapter() {
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
