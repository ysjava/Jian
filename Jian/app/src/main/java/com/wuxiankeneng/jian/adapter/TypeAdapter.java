package com.wuxiankeneng.jian.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.model.shop.TypeBean;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.ShopActivity;

import net.qiujuer.genius.res.Resource;

import java.util.ArrayList;

import butterknife.BindView;

public class TypeAdapter extends RecyclerAdapter<TypeBean> {
    public int selectTypeId = 1;
    private Context context;
    private ArrayList<TypeBean> list;

    public TypeAdapter(AdapterListener<TypeBean> mListener, Context context) {
        super(mListener);
        this.context = context;
    }

    public TypeAdapter(AdapterListener<TypeBean> mListener, Context context, ArrayList<TypeBean> list) {
        super(list, mListener);
        this.context = context;
        this.list = list;
    }


    @Override
    protected int getItemViewType(int position, TypeBean typeBean) {
        return R.layout.item_type;
    }

    @Override
    protected RecyclerAdapter.ViewHolder<TypeBean> onCreateViewHolder(View view, int viewType) {

        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<TypeBean> {
        @BindView(R.id.txt_type)
        TextView type;
        @BindView(R.id.txt_count)
        TextView count;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(TypeBean typeBean) {
            type.setText(typeBean.getType());

            //当前类型的数量
            int typeCount = ((ShopActivity) context).getCurrentTypeCount(typeBean.getTypeId());
            if (typeCount < 1) {
                count.setVisibility(View.GONE);
            } else {
                count.setText(String.valueOf(typeCount));
                count.setVisibility(View.VISIBLE);
            }


            if (selectTypeId == typeBean.getTypeId()) {
                itemView.setBackgroundColor(Resource.Color.WHITE);
            } else {
                itemView.setBackgroundColor(Color.TRANSPARENT);
            }

        }

    }
}
