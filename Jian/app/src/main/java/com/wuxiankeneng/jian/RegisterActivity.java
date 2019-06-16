package com.wuxiankeneng.jian;

import android.util.Log;

import com.wuxiankeneng.jian.activity.BaseActivityView;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
public class RegisterActivity extends BaseActivityView<RegisterPresenter>
        implements RegisterContract.View {

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.register();
    }

    @Override
    public void registerSuccess() {
        Log.e("TAGa", "注册成功");
    }
}
