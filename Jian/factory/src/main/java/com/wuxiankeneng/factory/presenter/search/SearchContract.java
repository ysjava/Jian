package com.wuxiankeneng.factory.presenter.search;

import com.wuxiankeneng.common.factory.base.BaseContract;

public interface SearchContract {
    interface View extends BaseContract.RecyclerView{
    }

   interface Presenter extends BaseContract.Presenter<View>{
        void searchShop(String shopName);
   }
}
