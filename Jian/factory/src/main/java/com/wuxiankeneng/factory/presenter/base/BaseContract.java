package com.wuxiankeneng.factory.presenter.base;

import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;

/**
 * Created by White paper on 2019/6/14
 * Describe :
 */
public interface BaseContract {
    interface View<T extends Presenter> {
        //加载框
        void showLoading();

        //设置p
        void setPresenter(T presenter);
    }

    interface Presenter {
        // 共用的开始触发
        void start();

        // 共用的销毁触发
        void destroy();
    }

    interface RecyclerView<T extends Presenter, ViewModel> extends View<T> {
        //拿到一个适配器
        RecyclerAdapter<ViewModel> getRecyclerAdapter();

        //适配器更改了数据后,可调用做一些事,比如关闭加载框啥的
        void onAdapterDataChanged();
    }

}
