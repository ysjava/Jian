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
            RegisterModel model = new RegisterModel(phone, password, Account.getPushId(),getName(),"b271a08d-d5b8-4b15-8422-237315ae5380");
            AccountHelper.register(model, this);
        }
    }

    //从名字集合随机抽取一个
    private String getName() {
        String[] names = new String[]{
                "杨大山", "杨小山", "杨前锋", "杨马褂", "杨二麻",
                "赵破胆", "赵明灯", "赵灯笼", "赵几几", "赵三娃",
                "李太保", "李大妈", "李大框", "李青米", "李好吗",
                "钱老爷", "钱多多", "钱面看", "钱依琳", "钱哥哥"
        };
        int i = (int) (Math.random() * 19);
        return names[i] + (int) (Math.random() * 1000);
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
