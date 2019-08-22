package com.wuxiankeneng.factory.tools;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

public class DiffUiDataCallback<T extends DiffUiDataCallback.UiDataDiffer<T>> extends DiffUtil.Callback {
    private List<T> mOldList, mNewList;

    public DiffUiDataCallback(List<T> mOldList, List<T> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
    }

    @Override
    public int getOldListSize() {
        //旧数据大小
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        //新数据大小
        return mNewList.size();
    }

    //首先对比两个是不是同一个东西
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T beanOld = mOldList.get(oldItemPosition);
        T beanNew = mNewList.get(newItemPosition);
        //这儿一般就是进行id是否相等,相等即代表同一东西,然后才进行内容的对比
        return beanNew.isSame(beanOld);
    }

    //然后进一步判断内容是否相同
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T beanOld = mOldList.get(oldItemPosition);
        T beanNew = mNewList.get(newItemPosition);
        //进行内容的对比
        return beanNew.isUiContentSame(beanOld);
    }

    // 进行比较的数据类型
    // 范型的目的，就是你是和一个你这个类型的数据进行比较
    public interface UiDataDiffer<T> {
        // 传递一个旧的数据给你，问你是否和你标示的是同一个数据
        boolean isSame(T old);

        // 你和旧的数据对比，内容是否相同
        boolean isUiContentSame(T old);
    }
}
