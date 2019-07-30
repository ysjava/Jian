package com.wuxiankeneng.jian.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.tools.Arithmetic;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.ShopActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//在店铺界面的购物车适配器
public class SelectedGoodsAdapter extends RecyclerAdapter<Goods> {
    private Context mContext;


    public SelectedGoodsAdapter(List<Goods> mDataList, AdapterListener<Goods> mListener, Context mContext) {
        super(mDataList, mListener);
        this.mContext = mContext;
    }

    @Override
    protected int getItemViewType(int position, Goods goods) {
        return R.layout.item_selected_goods;
    }

    @Override
    protected RecyclerAdapter.ViewHolder<Goods> onCreateViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Goods> {
        @BindView(R.id.img_goods_icon)
        ImageView icon;
        @BindView(R.id.txt_goods_name)
        TextView name;
        @BindView(R.id.txt_price)
        TextView price;
        @BindView(R.id.txt_count)
        TextView count;

        private Goods goods;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Goods goods) {
            this.goods = goods;
            Glide.with(mContext)
                    .load(goods.getImg())
                    .placeholder(R.drawable.default_portrait)
                    .error(R.drawable.default_portrait)
                    .into(icon);
            name.setText(goods.getName());
            price.setText(String.valueOf(Arithmetic.round(goods.getPrice() * goods.getCount(), 2)));
            count.setText(String.valueOf(goods.getCount()));

        }

        @OnClick(R.id.img_plus)
        void plusClick() {
            ((ShopActivity) mContext).addGoods(goods, true);
        }

        @OnClick(R.id.img_reduce)
        void reduceClick() {
            ((ShopActivity) mContext).removeGoods(goods, true);
        }
    }
}
