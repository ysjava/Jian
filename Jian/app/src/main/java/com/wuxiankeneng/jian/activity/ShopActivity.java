package com.wuxiankeneng.jian.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.widget.BaseListAdapter;
import com.wuxiankeneng.common.widget.EmptyView;
import com.wuxiankeneng.common.widget.RoundAngleImageView;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;
import com.wuxiankeneng.factory.model.shop.TypeBean;
import com.wuxiankeneng.factory.presenter.shop.ShopContract;
import com.wuxiankeneng.factory.presenter.shop.ShopPresenter;
import com.wuxiankeneng.factory.tools.Arithmetic;
import com.wuxiankeneng.jian.CoordinatorStickyList;

import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.adapter.GoodsAdapter;
import com.wuxiankeneng.jian.adapter.SelectedGoodsAdapter;
import com.wuxiankeneng.jian.adapter.TypeAdapter;
import com.wuxiankeneng.jian.di.module.ActivityModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @BindView(R.id.txt_shop_name)
    TextView mName;
    @BindView(R.id.txt_delivery_date)
    TextView mDeliDate;//配送时间
    @BindView(R.id.txt_reserve)
    TextView mReserve;
    @BindView(R.id.txt_range)
    TextView mRange;
    @BindView(R.id.txt_notice)
    TextView mNotice;

    @BindView(R.id.edt_search_goods)
    EditText mSearch;
    @BindView(R.id.iv_shop_pic)
    ImageView mPic;
    @BindView(R.id.img_back)
    ImageView mBack;
    @BindView(R.id.card_view)
    RoundAngleImageView mIcon;
    @BindView(R.id.empty_view)
    EmptyView<ShopActivity> mEmptyView;
    //记录的上一次位置
    private int i = 0;
    TextView mClear;

    RecyclerView mRecyclerBottomSheet;

    //店铺的所有商品集合
    private List<Goods> goodsList = new ArrayList<>();

    //保存类型中的商品数量和
    private SparseIntArray mSelectTypeCount = new SparseIntArray();
    //保存选中的商品信息
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
        //返回按钮
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置占位布局
        mEmptyView.setView(this);
        mEmptyView.bind(mLayBottomSheet);
        setPlaceHolderView(mEmptyView);


        mTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        mPresenter.start();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    HashMap<String, Goods> resultMap = (HashMap<String, Goods>) (data != null ? data.getSerializableExtra("GOODS_LIST") : null);
                    if (resultMap == null)
                        return;
                    //数据回来时进行对原数据的覆盖,所以先清空还原原数据
                    mSelectTypeCount.clear();

                    goodsSparseArray.clear();

                    if (resultMap.size() > 0) {

                        //保存 转换后的 以选中商品数据
                        ArrayList<Goods> list = new ArrayList<>();
                        list.clear();
                        //有数据,进行覆盖
                        for (Goods goods : resultMap.values()) {
                            int typeCount = mSelectTypeCount.get(goods.getTypeId());
                            if (typeCount == 0) {
                                mSelectTypeCount.append(goods.getTypeId(), goods.getCount());
                            } else {
                                mSelectTypeCount.append(goods.getTypeId(), typeCount + goods.getCount());
                            }

                            // tag1
                            for (int i = 0; i < goodsList.size(); i++) {
                                if (goods.getsId().equals(goodsList.get(i).getsId())) {
                                    Goods gd = goodsList.get(i);
                                    gd.setCount(goods.getCount());
                                    list.add(goodsList.get(i));

                                } else if (!list.contains(goodsList.get(i))) {
                                    Goods gd = goodsList.get(i);
                                    gd.setCount(0);

                                }
                            }

                        }
                        //这儿由于返回的resultMap集合中的数据和原来的goodsList中的数据不是同一对象
                        //所以需要把resultMap中数据转换成goodsList中的数据,比如id为1的商品,回传后id不变
                        //但对象不再是原来的id为1的对象了. 在购物车界面进行加减时,需要操作同一对象,所以配合tag1进行转换
                        for (Goods goods : list) {
                            goodsSparseArray.put(goods.getsId(), goods);
                        }
                    }
                    //回来时已经没数据了,就对列表进行遍历复原
                    if (resultMap.size() == 0) {
                        for (Goods goods : goodsList) {
                            goods.setCount(0);
                        }
                        goodsSparseArray.clear();
                    }
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        update(true);
    }

    @Override
    public void reFresh() {
        super.reFresh();
        mPresenter.start();
    }

    @OnClick(R.id.edt_search_goods)
    public void testClick() {
        SearchActivity.show(this, shopId, goodsSparseArray);
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
        Goods gd = goodsSparseArray.get(goods.getsId());
        if (gd == null) {
            goods.setCount(1);
            goodsSparseArray.put(goods.getsId(), goods);
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
        Goods gd = goodsSparseArray.get(goods.getsId());
        if (gd != null) {
            if (gd.getCount() < 2) {
                //这儿减1后  对应商品的count就回到了0,不然的话,对应商品的count还是1,更新右边列表就会出问题
                gd.setCount(gd.getCount() - 1);
                goodsSparseArray.remove(goods.getsId());
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
            totalPrice = Arithmetic.round(Arithmetic.add(totalPrice, Arithmetic.mul(Double.parseDouble(goods.getPrice()), goods.getCount())), 2);

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

//    @OnClick(R.id.txt_go_pay)
//    public void payClick() {
//    }


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

    @Override
    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public void success(Shop shop) {
        //TODO 加个替换图和错误加载图
        //加载背景图
        Glide.with(ShopActivity.this)
                .load(shop.getImg())
                .into(mPic);
        //加载icon 店铺图标
        Glide.with(ShopActivity.this)
                .load(shop.getIcon())
                .into(mIcon);
        mName.setText(shop.getName());
        mNotice.setText(shop.getNotice());

        mDeliDate.setText(String.format("配送约%s分钟", shop.getDeliveryDate()));
        mRange.setText(showRange(shop.getDeliveryRange()));
        mReserve.setText(shop.isReserve() ? "支持预定" : "暂不支持预定");

        if (mPlaceHolderView != null)
            mPlaceHolderView.triggerOk();
    }

    private String showRange(int deliveryRange) {
        if (deliveryRange == Shop.RANGE_NULL)
            return "暂不支持配送";
        if (deliveryRange == Shop.RANGE_DORM)
            return "寝室";
        if (deliveryRange == Shop.RANGE_DOWNSTAIRS)
            return "楼下";
        return "询问商家";
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

}
