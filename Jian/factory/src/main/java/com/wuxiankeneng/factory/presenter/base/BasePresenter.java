package com.wuxiankeneng.factory.presenter.base;

/**
 * Created by White paper on 2019/6/14
 * Describe :
 */
@SuppressWarnings("unchecked")
public class BasePresenter<View extends BaseContract.View> implements BaseContract.Presenter {

    private View mView;

    public BasePresenter(View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }



    /**
     * @return view 只能用不能改
     */
    protected final View getView() {
        return mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        if (mView != null) {
            mView.setPresenter(null);
            mView = null;
        }
    }
}
