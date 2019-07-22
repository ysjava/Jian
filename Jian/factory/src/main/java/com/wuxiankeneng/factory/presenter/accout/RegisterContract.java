package com.wuxiankeneng.factory.presenter.accout;

import com.wuxiankeneng.common.factory.base.BaseContract;

public interface RegisterContract {
    interface View extends BaseContract.View {
        void registerSuccess();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void register(String phone,String password);
    }
}
