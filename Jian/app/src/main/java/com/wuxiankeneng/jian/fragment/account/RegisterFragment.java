package com.wuxiankeneng.jian.fragment.account;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxiankeneng.common.app.BaseFragment;
import com.wuxiankeneng.factory.presenter.accout.RegisterContract;
import com.wuxiankeneng.factory.presenter.accout.RegisterPresenter;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.fragment.BaseFragmentView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragmentView<RegisterPresenter>
        implements RegisterContract.View {
    private AccountTrigger mAccountTrigger;
    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_password)
    EditText mPassword;
    @BindView(R.id.btn_register)
    Button mRegister;
    @BindView(R.id.txt_switch_login)
    TextView mSwitchLogin;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void registerSuccess() {
        //注册成功就调到登陆界面
        mAccountTrigger.triggerView();
    }

    @OnClick(R.id.btn_register)
    public void registerClick() {
        String phoneNumber = mPhone.getText().toString().trim();
        String pawNumber = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(pawNumber)) {
            Toast.makeText(getContext(), "账号或密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        //发起注册
        mPresenter.register(phoneNumber, pawNumber);
    }

    //切换到登陆界面
    @OnClick(R.id.txt_switch_login)
    public void switchLoginClick() {
        mAccountTrigger.triggerView();
    }
}
