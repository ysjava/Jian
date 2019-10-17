package com.wuxiankeneng.jian.fragment;

import android.content.Context;

import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.common.factory.base.BasePresenter;
import com.wuxiankeneng.jian.App;

import com.wuxiankeneng.jian.di.component.DaggerFragmentComponent;
import com.wuxiankeneng.jian.di.component.FragmentComponent;
import com.wuxiankeneng.jian.di.module.FragmentModule;

import javax.inject.Inject;

/**
 * Created by White paper on 2019/6/14
 * Describe : 基础契约的Fragment的v层
 */
public abstract class BaseFragmentView<Presenter extends BasePresenter> extends BaseFragment
        implements BaseContract.View {

    @Inject
    protected Presenter mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化p
        initInject();
        mPresenter.attachView(this);
    }

    protected abstract void initInject();

    //拿到注入器
    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(initFragmentModule())
                .build();
    }

    protected FragmentModule initFragmentModule() {
        return new FragmentModule();
    }

    @Override
    public void showLoading() {
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerLoading();
        }
    }

    @Override
    public void reFresh() {

    }

    @Override
    public void showError(int str) {
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerError(str);
        } else
            Application.showToast(str);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }
}
