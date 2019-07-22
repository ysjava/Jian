package com.wuxiankeneng.factory.presenter.accout;

import android.text.TextUtils;


import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.common.factory.base.BasePresenter;
import com.wuxiankeneng.factory.R;
import com.wuxiankeneng.factory.db.Student;
import com.wuxiankeneng.factory.helper.AccountHelper;
import com.wuxiankeneng.factory.model.account.LoginModel;
import com.wuxiankeneng.factory.presenter.Account;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter,DataSource.Callback<Student> {
    @Inject
    public LoginPresenter() {
    }

    @Override
    public void login(String phone, String password) {
        LoginContract.View view = getView();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            view.showError(R.string.account_or_password_error);
        }else {
            LoginModel model = new LoginModel(phone,password, Account.getPushId());
            AccountHelper.login(model,this);
        }

    }

    @Override
    public void onDataLoaded(Student student) {
        final LoginContract.View view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.loginSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final LoginContract.View view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }
}
