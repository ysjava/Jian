package com.wuxiankeneng.jian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.widget.BaseListAdapter;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.tools.Arithmetic;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.ShopActivity;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.BindViews;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class GoodsAdapter extends BaseListAdapter<Goods> implements StickyListHeadersAdapter {

    private LayoutInflater inflater;

    private ShopActivity activity;

    public GoodsAdapter(Context context) {
        this.activity = (ShopActivity) context;
        this.inflater = LayoutInflater.from(context);
    }

    public GoodsAdapter(ArrayList<Goods> mDataList, Context context) {
        super(mDataList);

        this.activity = (ShopActivity) context;
        this.inflater = LayoutInflater.from(context);

    }


    /**
     * 添加头部数据
     */
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_header_view, parent, false);
            holder = new HeaderViewHolder();
            holder.textView = convertView.findViewById(R.id.txt_head);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //设置头部文本为商品类型
        holder.textView.setText(getItems().get(position).getType());

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return getItems().get(position).getTypeId();
    }

    @Override
    protected int getItemViewType(int position, Goods goods) {
        return R.layout.item_goods;
    }

    @Override
    protected BaseListAdapter.ViewHolder<Goods> onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }


    //右边列表的头部Holder
    class HeaderViewHolder {
        TextView textView;
    }

    class ViewHolder extends BaseListAdapter.ViewHolder<Goods> implements View.OnClickListener {

        private ImageView goodsIcon, goodsAdd, goodsMinus;
        private TextView goodsName, goodsSales, goodsPrice, goodsCount;

        private Goods goods;

        ViewHolder(View itemView) {
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
                    int count = activity.getCurrentGoodsCountById(goods.getId());
                    if (count < 1) {
                        goodsCount.setVisibility(View.VISIBLE);
                        goodsMinus.setVisibility(View.VISIBLE);
                    }
                    count++;
                    activity.addGoods(goods, false);
                    goodsCount.setText(String.valueOf(count));
                }
                break;
                case R.id.iv_minus:
                    int count = activity.getCurrentGoodsCountById(goods.getId());
                    if (count < 2) {
                        goodsCount.setVisibility(View.GONE);
                        goodsMinus.setVisibility(View.GONE);
                    }
                    count--;
                    activity.removeGoods(goods, false);
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
}
