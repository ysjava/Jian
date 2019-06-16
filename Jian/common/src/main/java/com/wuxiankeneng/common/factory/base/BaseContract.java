package com.wuxiankeneng.common.factory.base;

import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;

/**
 * Created by White paper on 2019/6/14
 * Describe :
 */
public interface BaseContract {
    interface View{
        //加载框
        void showLoading();
    }

    interface Presenter<V extends View> {
        // 共用的开始触发
        void start();

        // 共用的销毁触发
        void destroy();

        void attachView(V view);
    }

    interface RecyclerView<ViewModel> extends View {
        //拿到一个适配器
        RecyclerAdapter<ViewModel> getRecyclerAdapter();

        //适配器更改了数据后,可调用做一些事,比如关闭加载框啥的
        void onAdapterDataChanged();
    }

}
