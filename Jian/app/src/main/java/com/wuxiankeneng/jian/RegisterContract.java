package com.wuxiankeneng.jian;

import com.wuxiankeneng.common.factory.base.BaseContract;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
public interface RegisterContract {
    interface View extends BaseContract.View{
        void registerSuccess();
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void register();
    }
}
