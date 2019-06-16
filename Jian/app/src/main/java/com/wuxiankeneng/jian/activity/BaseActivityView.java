package com.wuxiankeneng.jian.activity;

import com.wuxiankeneng.common.app.BaseActivity;
import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.common.factory.base.BasePresenter;
import com.wuxiankeneng.jian.App;
import com.wuxiankeneng.jian.di.component.ActivityComponent;
import com.wuxiankeneng.jian.di.component.DaggerActivityComponent;
import com.wuxiankeneng.jian.di.module.ActivityModule;

import android.util.Log;

import javax.inject.Inject;


/**
 * Created by White paper on 2019/6/14
 * Describe : 基础契约的Activity的v层
 */
public abstract class BaseActivityView<Presenter extends BasePresenter> extends BaseActivity
        implements BaseContract.View {

    @Inject
    protected Presenter mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void initBefore() {
        super.initBefore();
        initInject();
        mPresenter.attachView(this);
    }

    //拿到注入器
    protected ActivityComponent getActivityComponent() {

        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(initActivityModule())
                .build();
    }



    @Override
    public void showLoading() {
        //TODO 显示loading
    }

    //子类注入
    protected abstract void initInject();

    //实例化需要传参就交给子类   不需要传的话就使用默认的空构造
    protected  ActivityModule initActivityModule(){
           return new ActivityModule();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAGa", "销毁: 我是base act");
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }
}
