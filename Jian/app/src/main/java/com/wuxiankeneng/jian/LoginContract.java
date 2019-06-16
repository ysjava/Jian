package com.wuxiankeneng.jian;

import com.wuxiankeneng.common.factory.base.BaseContract;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
public interface LoginContract {

    interface View extends BaseContract.View{
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void login();
    }
}
