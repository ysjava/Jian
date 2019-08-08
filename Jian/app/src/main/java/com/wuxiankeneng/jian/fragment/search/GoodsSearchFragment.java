package com.wuxiankeneng.jian.fragment.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.app.BaseFragment;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.presenter.search.GoodsSearchContract;
import com.wuxiankeneng.factory.presenter.search.GoodsSearchPresenter;
import com.wuxiankeneng.factory.tools.Arithmetic;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.SearchActivity;
import com.wuxiankeneng.jian.activity.ShopActivity;
import com.wuxiankeneng.jian.adapter.SelectedGoodsAdapter;
import com.wuxiankeneng.jian.adapter.SelectedGoodsAdapterTwo;
import com.wuxiankeneng.jian.fragment.BaseFragmentView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


public class GoodsSearchFragment extends BaseFragmentView<GoodsSearchPresenter>
        implements SearchActivity.SearchFragment, GoodsSearchContract.View {
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    RecyclerAdapter<Goods> adapter;
    //点击购物车后的底部弹出视图
    View mBottomSheetView;
    @BindView(R.id.lay_bottom_sheet)
    BottomSheetLayout mLayBottomSheet;
    TextView mClear;
    RecyclerView mRecyclerBottomSheet;
    //购物车里面的已点单信息适配器
    SelectedGoodsAdapterTwo mSelectedAdapterTwo;
    private SearchActivity activity;
    @BindView(R.id.txt_total)
    TextView mTotal;
    @BindView(R.id.txt_go_pay)
    TextView mGoPay;
    @BindView(R.id.txt_total_price)
    TextView mTotalPrice;
    private Map<String, Goods> mSelectedGoodsList;

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
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        activity = (SearchActivity) getActivity();
        assert activity != null;
        mSelectedGoodsList = activity.getMap();
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
        adapter.addFooterView(R.layout.item_type_footer);

    }

    @Override
    protected void initData() {
        super.initData();
        List<Goods> goodsList = new ArrayList<>();

        Goods goods;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                goods = new Goods("" + i + j, "商品" + (i * 100 + j),
                        "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/portrait/201812/5e2f33d5b2c89271b736e5f9c387ef91.jpg",
                        String.valueOf((int) (Math.random() * 1000)), "类型" + i, i, Math.random() * 100);
                goodsList.add(goods);
            }

        }

        mPresenter.onDataLoaded(goodsList);
    }

    @Override
    public Map<String, Goods> getSelectedGoodsList() {
        return mSelectedGoodsList;
    }

    @Override
    public void updateShopping() {
        update();
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Goods> implements View.OnClickListener {
        private ImageView goodsIcon, goodsAdd, goodsMinus;
        private TextView goodsName, goodsSales, goodsPrice, goodsCount;
        private Goods goods;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsIcon = itemView.findViewById(R.id.iv_goods_icon);
            goodsAdd = itemView.findViewById(R.id.iv_add);
            goodsMinus = itemView.findViewById(R.id.iv_minus);
            goodsName = itemView.findViewById(R.id.txt_goods_name);
            goodsSales = itemView.findViewById(R.id.txt_sales);
            goodsPrice = itemView.findViewById(R.id.txt_price);
            goodsCount = itemView.findViewById(R.id.txt_count);

            goodsAdd.setOnClickListener(this);
            goodsMinus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.iv_add: {
                    //判断当前点击的商品选中的数量
                    int count = getCurrentGoodsCountById(goods.getId());
                    if (count < 1) {
                        goodsCount.setVisibility(View.VISIBLE);
                        goodsMinus.setVisibility(View.VISIBLE);
                    }
                    count++;
                    addGoods(goods);
                    goodsCount.setText(String.valueOf(count));
                }
                break;
                case R.id.iv_minus:
                    int count = getCurrentGoodsCountById(goods.getId());
                    if (count < 2) {
                        goodsCount.setVisibility(View.GONE);
                        goodsMinus.setVisibility(View.GONE);
                    }
                    count--;
                    removeGoods(goods);
                    goodsCount.setText(String.valueOf(count));
                    break;
            }
        }

        @Override
        protected void onBind(Goods goods) {
            this.goods = goods;
            Glide.with(activity)
                    .load(goods.getImg())
                    .dontAnimate()
                    .error(R.drawable.default_portrait)
                    .placeholder(R.drawable.default_portrait)
                    .into(goodsIcon);
            goodsName.setText(goods.getName());
            goodsSales.setText(String.format("销量 :%s", goods.getSales()));
            goodsPrice.setText(String.valueOf(Arithmetic.round(goods.getPrice(), 2)));
//            goods.setCount(activity.getCurrentGoodsCountById(goods.getId()));
            goodsCount.setText(String.valueOf(goods.getCount()));

            if (goods.getCount() < 1) {
                goodsCount.setVisibility(View.GONE);
                goodsMinus.setVisibility(View.GONE);
            } else {
                goodsMinus.setVisibility(View.VISIBLE);
                goodsCount.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public RecyclerAdapter<Goods> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    @OnClick(R.id.lay_shopping_cart)
    public void shoppingCartClick() {
        if (mSelectedGoodsList.values().size() == 0)
            return;

        if (mBottomSheetView == null) {
            mBottomSheetView = createBottomSheetView();
        }

        if (mLayBottomSheet.isSheetShowing()) {
            mLayBottomSheet.dismissSheet();
        } else
            mLayBottomSheet.showWithSheetView(mBottomSheetView);

    }


    //创建弹出视图
    private View createBottomSheetView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.lay_bottom_sheet,
                (ViewGroup) Objects.requireNonNull(getActivity()).getWindow().getDecorView(), false);
        mClear = view.findViewById(R.id.txt_clear);
        mRecyclerBottomSheet = view.findViewById(R.id.recycler_bottom_sheet);
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Goods value : mSelectedGoodsList.values()) {
                    value.setCount(0);
                }
                mSelectedGoodsList.clear();
                update();
            }
        });
        mRecyclerBottomSheet.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerBottomSheet.setAdapter(mSelectedAdapterTwo = new SelectedGoodsAdapterTwo(new ArrayList<>(mSelectedGoodsList.values()),
                null, getContext(), this));
        return view;
    }

    //拿到当前商品id的已选择数量
    public int getCurrentGoodsCountById(String goodsId) {
        Goods goods = mSelectedGoodsList.get(goodsId);
        if (goods == null)
            return 0;
        else
            return goods.getCount();
    }

    //商品添加
    public void addGoods(Goods goods) {
        //添加到商品集合
        Goods gd = mSelectedGoodsList.get(goods.getId());
        if (gd == null) {
            goods.setCount(1);
            mSelectedGoodsList.put(goods.getId(), goods);
        } else {
            gd.setCount(gd.getCount() + 1);
        }

        update();
    }


    //商品删除
    public void removeGoods(Goods goods) {

        //从商品集合删除
        Goods gd = mSelectedGoodsList.get(goods.getId());
        if (gd != null) {
            if (gd.getCount() < 2) {
                //这儿减1后  对应商品的count就回到了0,不然的话,对应商品的count还是1,更新右边列表就会出问题
                gd.setCount(gd.getCount() - 1);
                mSelectedGoodsList.remove(goods.getId());
            } else {
                gd.setCount(gd.getCount() - 1);
            }
        }

        update();
    }

    //刷新页面的相关信息
    private void update() {
        int totalCount = 0;
        double totalPrice = 0;
        for (Goods goods : mSelectedGoodsList.values()) {
            totalCount += goods.getCount();
            totalPrice = Arithmetic.round(Arithmetic.add(totalPrice, Arithmetic.mul(goods.getPrice(), goods.getCount())), 2);

        }

        if (totalCount < 1) {
            mTotal.setVisibility(View.GONE);
            mGoPay.setEnabled(false);
        } else {
            mTotal.setVisibility(View.VISIBLE);
            mGoPay.setEnabled(true);
        }
        mTotal.setText(String.valueOf(totalCount));

        mTotalPrice.setText(String.format("%s", Arithmetic.round(totalPrice, 2)));

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        //更新购物车的信息
        if (mSelectedAdapterTwo != null) {
            mSelectedAdapterTwo.replace(mSelectedGoodsList.values());
            mSelectedAdapterTwo.notifyDataSetChanged();
        }

        if (mLayBottomSheet.isSheetShowing() && mSelectedGoodsList.size() <= 0) {
            mLayBottomSheet.dismissSheet();
        }


    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }
}
