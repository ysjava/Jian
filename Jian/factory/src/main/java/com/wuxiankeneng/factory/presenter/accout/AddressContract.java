package com.wuxiankeneng.factory.presenter.accout;

import com.wuxiankeneng.common.factory.base.BaseContract;
import com.wuxiankeneng.factory.card.AddressCard;

/**
 * Created by White paper on 2019/9/4
 * Describe :
 */
public interface AddressContract {
    interface View extends BaseContract.RecyclerView<AddressCard> {

    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
