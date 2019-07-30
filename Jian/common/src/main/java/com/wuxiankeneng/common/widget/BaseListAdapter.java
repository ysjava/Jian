package com.wuxiankeneng.common.widget;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wuxiankeneng.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseListAdapter<Data> extends BaseAdapter {

    private List<Data> mDataList;

    public BaseListAdapter() {
        this(new ArrayList<Data>());
    }

    public BaseListAdapter(List<Data> mDataList) {
        this.mDataList = mDataList;
    }

    /**
     * 返回整个集合
     */
    public List<Data> getItems() {
        return mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder<Data> viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //拿到LayoutInflater
        if (convertView == null) {
            convertView = inflater.inflate(getItemViewType(position, mDataList.get(position)), viewGroup, false);
            viewHolder = onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder<Data>) convertView.getTag();
        }
        //界面绑定
//        ButterKnife.bind(viewHolder, convertView);
        //进行数据绑定
        viewHolder.bind(mDataList.get(position));

        return convertView;
    }

    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    //通过实现类拿到ViewHolder
    protected abstract ViewHolder<Data> onCreateViewHolder(View view);


    public static abstract class ViewHolder<Data> {
//        public ViewHolder(View itemView) {
//        }

        void bind(Data data) {
            onBind(data);
        }


        //交由子类完成数据绑定
        protected abstract void onBind(Data data);
    }

    /**
     * 替换为一个新的集合，其中包括了清空
     *
     * @param dataList 一个新的集合
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList == null || dataList.size() == 0)
            return;
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }
}
