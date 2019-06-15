package com.wuxiankeneng.factory.presenter.base;

import android.content.Context;

import com.wuxiankeneng.common.app.BaseFragment;

/**
 * Created by White paper on 2019/6/14
 * Describe : 基础契约的Fragment的v层
 */
public abstract class BaseFragmentView<Presenter extends BaseContract.Presenter> extends BaseFragment
        implements BaseContract.View<Presenter> {

    private Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化p
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
}
