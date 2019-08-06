package com.wuxiankeneng.jian.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.app.BaseActivity;
import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.common.widget.BaseListAdapter;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.model.shop.TypeBean;
import com.wuxiankeneng.factory.presenter.shop.ShopContract;
import com.wuxiankeneng.factory.presenter.shop.ShopPresenter;
import com.wuxiankeneng.factory.tools.Arithmetic;
import com.wuxiankeneng.factory.tools.DensityUtils;
import com.wuxiankeneng.jian.App;
import com.wuxiankeneng.jian.CoordinatorStickyList;

import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.adapter.GoodsAdapter;
import com.wuxiankeneng.jian.adapter.SelectedGoodsAdapter;
import com.wuxiankeneng.jian.adapter.TypeAdapter;
import com.wuxiankeneng.jian.di.module.ActivityModule;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopActivity extends BaseActivityView<ShopPresenter>
        implements View.OnClickListener, ShopContract.View {
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.itemListView)
    CoordinatorStickyList mItemListView;
    @BindView(R.id.recycler)
    RecyclerView mTypeRecyclerView;
    @BindView(R.id.lay_bottom_sheet)
    BottomSheetLayout mLayBottomSheet;
    //左边分类的适配器
    TypeAdapter adapter;
    //购物车里面的已点单信息适配器
    SelectedGoodsAdapter mSelectedAdapter;
    //右边商品的适配器
    GoodsAdapter mGoodsAdapter;
    //点击购物车后的底部弹出视图
    View mBottomSheetView;
    @BindView(R.id.txt_total)
    TextView mTotal;
    @BindView(R.id.txt_total_price)
    TextView mTotalPrice;
    @BindView(R.id.txt_go_pay)
    TextView mGoPay;
    @BindView(R.id.edt_search_goods)
    EditText mSearch;

    @BindView(R.id.img_back)
    ImageView mBack;
    //记录的上一次位置
    private int i = 0;
    TextView mClear;
    LinearLayoutManager manager;
    RecyclerView mRecyclerBottomSheet;

    private ArrayList<Goods> goodsList = new ArrayList<>();

    //保存类型中的商品数量和
    private SparseIntArray mSelectTypeCount = new SparseIntArray();
    //保存商品信息
    private HashMap<String, Goods> goodsSparseArray = new HashMap<>();

    private String shopId;

    public static void show(Context context, String shopId) {
        Intent intent = new Intent(context, ShopActivity.class);
        intent.putExtra("shopId", shopId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_shop;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        shopId = bundle.getString("shopId");
        return super.initArgs(bundle);
    }

    @Override
    protected ActivityModule initActivityModule() {
        return new ActivityModule(shopId);
    }


    @Override
    protected void initWidget() {
        super.initWidget();


        mTypeRecyclerView.setLayoutManager(manager = new LinearLayoutManager(this));

        mTypeRecyclerView.setAdapter(adapter = new TypeAdapter(new RecyclerAdapter.AdapterListenerImpl<TypeBean>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder viewHolder, TypeBean typeBean) {
                onTypeClicked(typeBean.getTypeId());
            }
        }, this));
        //添加列表头部布局
//        adapter.addHeaderView(R.layout.item_header_view,new TypeBean("null",-1));
        //添加列表尾部布局
        adapter.addFooterView(R.layout.item_type_footer);


        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                //i这个值就是当完全展开就是0       收完就是x
                int verticalOffset = Math.abs(i);
                //这个就是拿到最大值   就是完全收拢时的值
                float totalScrollRange = appBarLayout.getTotalScrollRange();
                //进度
                float progress = verticalOffset / totalScrollRange;

                if (progress >= 0.5) {
                    mBack.setImageResource(R.drawable.ic_back_shop);
                    mSearch.setVisibility(View.VISIBLE);
                } else {
                    mBack.setImageResource(R.drawable.ic_back);
                    mSearch.setVisibility(View.GONE);
                }
            }
        });
        mItemListView.addFooterView(getLayoutInflater().inflate(R.layout.item_goods_footer, null));
        mItemListView.setAdapter(mGoodsAdapter = new GoodsAdapter(this));

        mItemListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (goodsList.size() == 0)
                    return;
                Goods goods = goodsList.get(firstVisibleItem);
                //判断第一个item的类型id是否是选择的类型id,不是就赋给它


                if (adapter.selectTypeId != goods.getTypeId()) {
                    adapter.selectTypeId = goods.getTypeId();
                    //赋给它后就刷新,然后会adapter会更具当前selectTypeId进行移动左边的类型栏
                    adapter.notifyDataSetChanged();
                    //记录左边type的位置
                    int currentPos = getSelectedGroupPosition(goods.getTypeId());

                    mTypeRecyclerView.smoothScrollToPosition(getSelectedGroupPosition(goods.getTypeId()));
                    //如果当前位置大于8,就进行移动位置
                    if (currentPos > 8) {
                        //i为记录上一次的位置,用于对比当前位置进行判断是该上移还是下移
                        if (currentPos > i)
                            mTypeRecyclerView.scrollBy(0, (int) getResources().getDimension(R.dimen.dp_50));
                        else
                            mTypeRecyclerView.scrollBy(0, -(int) getResources().getDimension(R.dimen.dp_50));
                    }
                    //记录
                    i = currentPos;

                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

    }


    @OnClick(R.id.card_view)
    public void test() {
        Application.showToast("你好");
    }

    public void onTypeClicked(int typeId) {
        //左边的item点击联动右边的listview滑动
        mItemListView.setSelection(getSelectedPosition(typeId));
    }

    private int getSelectedPosition(int typeId) {
        int position = 0;
        for (int i = 0; i < goodsList.size(); i++) {
            if (goodsList.get(i).getTypeId() == typeId) {
                position = i;
                break;
            }
        }
        return position;

    }

    //根据类别id获取分类的Position 用于滚动左侧的类别列表
    public int getSelectedGroupPosition(int typeId) {
        for (int i = 0; i < adapter.getItems().size(); i++) {
            if (typeId == adapter.getItems().get(i).getTypeId()) {
                return i;
            }
        }
        return 0;
    }

    @OnClick(R.id.lay_shopping_cart)
    public void shoppingCartClick() {
        if (goodsSparseArray.size() == 0)
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
        View view = LayoutInflater.from(this).inflate(R.layout.lay_bottom_sheet, (ViewGroup) getWindow().getDecorView(), false);
        mClear = view.findViewById(R.id.txt_clear);
        mRecyclerBottomSheet = view.findViewById(R.id.recycler_bottom_sheet);
        mClear.setOnClickListener(this);
        mRecyclerBottomSheet.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerBottomSheet.setAdapter(mSelectedAdapter = new SelectedGoodsAdapter(new ArrayList<>(goodsSparseArray.values()), null, this));
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_clear:
                mSelectTypeCount.clear();

                for (Goods goods : goodsSparseArray.values()) {
                    goods.setCount(0);
                }

                goodsSparseArray.clear();
                update(true);
                break;
        }
    }

    //返回当前类型中选中商品的总数量
    public int getCurrentTypeCount(int typeId) {
        return mSelectTypeCount.get(typeId);
    }

    //拿到当前商品id的已选择数量
    public int getCurrentGoodsCountById(String goodsId) {
        Goods goods = goodsSparseArray.get(goodsId);
        if (goods == null)
            return 0;
        else
            return goods.getCount();
    }

    //商品添加
    public void addGoods(Goods goods, boolean isRefresh) {
        int typeCount = mSelectTypeCount.get(goods.getTypeId());
        if (typeCount == 0) {
            mSelectTypeCount.append(goods.getTypeId(), 1);
        } else {
            mSelectTypeCount.append(goods.getTypeId(), ++typeCount);
        }

        //添加到商品集合
        Goods gd = goodsSparseArray.get(goods.getId());
        if (gd == null) {
            goods.setCount(1);
            goodsSparseArray.put(goods.getId(), goods);
        } else {
            gd.setCount(gd.getCount() + 1);
        }

        update(isRefresh);
    }


    //商品删除
    public void removeGoods(Goods goods, boolean isRefresh) {
        int typeCount = mSelectTypeCount.get(goods.getTypeId());
        if (typeCount == 1) {
            mSelectTypeCount.delete(goods.getTypeId());
        } else if (typeCount > 1) {
            typeCount--;
            mSelectTypeCount.append(goods.getTypeId(), typeCount);
        }

        //从商品集合删除
        Goods gd = goodsSparseArray.get(goods.getId());
        if (gd != null) {
            if (gd.getCount() < 2) {
                //这儿减1后  对应商品的count就回到了0,不然的话,对应商品的count还是1,更新右边列表就会出问题
                gd.setCount(gd.getCount() - 1);
                goodsSparseArray.remove(goods.getId());
            } else {
                gd.setCount(gd.getCount() - 1);
            }
        }

        update(isRefresh);
    }

    //刷新页面的相关信息
    private void update(boolean isRefresh) {
        int totalCount = 0;
        double totalPrice = 0;
        for (Goods goods : goodsSparseArray.values()) {
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


        //更新类型里的数量
        if (adapter != null)
            adapter.notifyDataSetChanged();

        //更新购物车的信息
        if (mSelectedAdapter != null) {
            mSelectedAdapter.replace(goodsSparseArray.values());
            mSelectedAdapter.notifyDataSetChanged();
        }

        if (mLayBottomSheet.isSheetShowing() && goodsSparseArray.size() <= 0) {
            mLayBottomSheet.dismissSheet();
        }

        //更新右边商品信息
        if (isRefresh) {
            if (mGoodsAdapter != null)
                mGoodsAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.card_view)
    public void payClick() {
//        Application.showToast(shopId);
        goodsList.clear();
        Goods goods;
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 10; j++) {
                goods = new Goods("" + i + j, "商品" + (i * 100 + j), "",
                        String.valueOf((int) (Math.random() * 1000)), "类型" + i, i, Math.random() * 100);
                goodsList.add(goods);
            }

        }

        Shop shop = new Shop();
        shop.setId("");
        shop.setsId("");
        shop.setName("");
        shop.setDesc("");
        shop.setImg("");
        shop.setIcon("");
        shop.setReserve(true);
        shop.setDeliveryRange(1);
        shop.setSales("");
        shop.setDeliveryDate(50);
        shop.setNotice("");
        shop.setRecommendGoods(null);
        shop.setAllGoods(goodsList);


        mPresenter.onDataLoaded(shop);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public RecyclerAdapter<TypeBean> getTypeAdapter() {
        return adapter;
    }

    @Override
    public BaseListAdapter<Goods> getGoodsAdapter() {
        return mGoodsAdapter;
    }
}
