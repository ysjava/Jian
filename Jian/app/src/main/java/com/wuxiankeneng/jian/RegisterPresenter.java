package com.wuxiankeneng.jian;

import android.util.Log;

import com.wuxiankeneng.common.factory.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter {
    @Inject
    public RegisterPresenter() {
//        super(mView);
    }

    @Override
    public void register() {
        Log.e("TAGa", "开始注册");
        getView().registerSuccess();
    }
}
