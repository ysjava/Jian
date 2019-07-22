package com.wuxiankeneng.jian.fragment.account;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxiankeneng.factory.presenter.accout.LoginContract;
import com.wuxiankeneng.factory.presenter.accout.LoginPresenter;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.AccountActivity;
import com.wuxiankeneng.jian.fragment.BaseFragmentView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragmentView<LoginPresenter>
        implements LoginContract.View {
    private AccountTrigger mAccountTrigger;

    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_password)
    EditText mPassword;
    @BindView(R.id.btn_login)
    Button mLogin;
    @BindView(R.id.txt_new_register)
    TextView mRegister;
    @BindView(R.id.txt_forget_password)
    TextView mForgetPassword;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void loginSuccess() {
        //TODO 登陆成功就回到myFragment,接口回掉回去更新信息
        Objects.requireNonNull(getActivity()).finish();
    }

    @OnClick(R.id.btn_login)
    public void loginClick() {
        String phoneNumber = mPhone.getText().toString().trim();
        String pawNumber = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(pawNumber)) {
            Toast.makeText(getContext(), "账号或密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        //发起登陆
        mPresenter.login(phoneNumber,pawNumber);
    }

    @OnClick(R.id.txt_forget_password)
    public void forgetPawClick(){
        //TODO 忘记密码
    }

    @OnClick(R.id.txt_new_register)
    public void registerClick(){
        //切换到注册界面
        mAccountTrigger.triggerView();
    }
}
