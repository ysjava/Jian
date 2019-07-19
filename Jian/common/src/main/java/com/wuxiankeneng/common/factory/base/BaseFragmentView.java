package com.wuxiankeneng.common.factory.base;

import android.content.Context;

import com.wuxiankeneng.common.app.BaseFragment;

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

//    protected final Presenter getPresenter() {
//        return mPresenter;
//    }

    //    protected abstract Presenter initPresenter();
    protected abstract void initInject();

    //拿到注入器


    @Override
    public void showLoading() {
        //TODO 显示loading
    }

}
