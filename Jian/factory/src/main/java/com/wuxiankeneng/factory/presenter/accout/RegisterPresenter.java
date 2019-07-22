package com.wuxiankeneng.factory.presenter.accout;

import android.text.TextUtils;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BasePresenter;
import com.wuxiankeneng.factory.R;
import com.wuxiankeneng.factory.db.Student;
import com.wuxiankeneng.factory.helper.AccountHelper;
import com.wuxiankeneng.factory.model.account.RegisterModel;
import com.wuxiankeneng.factory.presenter.Account;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import javax.inject.Inject;

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<Student> {
    @Inject
    public RegisterPresenter() {
    }


    @Override
    public void register(String phone, String password) {
        RegisterContract.View view = getView();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            view.showError(R.string.account_or_password_error);
        } else {
            RegisterModel model = new RegisterModel(phone, password, Account.getPushId());
            AccountHelper.register(model, this);
        }
    }

    @Override
    public void onDataLoaded(Student student) {
        final RegisterContract.View view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.registerSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final RegisterContract.View view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }
}
