package com.wuxiankeneng.factory.presenter.accout;

import com.wuxiankeneng.common.factory.base.BaseContract;

public interface LoginContract {
    interface View extends BaseContract.View{
        //登陆成功
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter<View>{
        //发起登陆
        void login(String phone,String password);
    }
}
