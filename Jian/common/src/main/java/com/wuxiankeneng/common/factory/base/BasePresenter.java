package com.wuxiankeneng.common.factory.base;


import android.util.Log;

/**
 * Created by White paper on 2019/6/14
 * Describe :
 */
@SuppressWarnings("unchecked")
public class BasePresenter<View extends BaseContract.View> implements BaseContract.Presenter<View> {

    private View mView;
    public BasePresenter() {
    }

    /**
     * @return view 只能用不能改
     */
    protected final View getView() {
        return mView;
    }

    @Override
    public void start() {
        if (mView != null) {
            mView.showLoading();
        }
    }

    @Override
    public void destroy() {
        Log.e("TAGa", "销毁: 我是base pre");
        if (mView != null)
            mView = null;

    }

    @Override
    public void attachView(View view) {
        this.mView = view;
    }

}
