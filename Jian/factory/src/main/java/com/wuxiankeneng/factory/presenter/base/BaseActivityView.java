package com.wuxiankeneng.factory.presenter.base;

import com.wuxiankeneng.common.app.BaseActivity;


/**
 * Created by White paper on 2019/6/14
 * Describe : 基础契约的Activity的v层
 */
public abstract class BaseActivityView<Presenter extends BaseContract.Presenter> extends BaseActivity
        implements BaseContract.View<Presenter> {


    protected Presenter mPresenter;

    @Override
    protected void initBefore() {
        super.initBefore();
        initPresenter();
    }


    protected final Presenter getPresenter() {
        return mPresenter;
    }

    protected abstract Presenter initPresenter();

    @Override
    public void showLoading() {
        //TODO 显示loading
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.mPresenter = presenter;
    }

    protected abstract void initInject();
}
