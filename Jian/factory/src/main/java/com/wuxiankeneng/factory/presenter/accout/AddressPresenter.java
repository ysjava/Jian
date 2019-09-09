package com.wuxiankeneng.factory.presenter.accout;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BasePresenter;
import com.wuxiankeneng.common.factory.base.BaseRecyclerPresenter;
import com.wuxiankeneng.factory.card.AddressCard;
import com.wuxiankeneng.factory.helper.AccountHelper;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by White paper on 2019/9/4
 * Describe :
 */
public class AddressPresenter extends BaseRecyclerPresenter<AddressCard, AddressContract.View>
        implements AddressContract.Presenter, DataSource.Callback<List<AddressCard>> {
    @Inject
    public AddressPresenter() {
    }

    @Override
    public void start() {
        super.start();
        AccountHelper.getAddressList(this);
    }

    @Override
    public void onDataLoaded(final List<AddressCard> addressCards) {
        final AddressContract.View view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (view != null) {
                    view.getRecyclerAdapter().replace(addressCards);
                }
            }
        });

    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final AddressContract.View view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (view != null) {
                    view.showError(strRes);
                }
            }
        });
    }

}
