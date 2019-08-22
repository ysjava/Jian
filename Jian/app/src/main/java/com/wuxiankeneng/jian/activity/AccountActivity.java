package com.wuxiankeneng.jian.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.wuxiankeneng.common.app.BaseActivity;
import com.wuxiankeneng.jian.MainActivity;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.fragment.account.AccountTrigger;
import com.wuxiankeneng.jian.fragment.account.LoginFragment;
import com.wuxiankeneng.jian.fragment.account.RegisterFragment;

import java.io.Serializable;

import butterknife.BindView;

public class AccountActivity extends BaseActivity
        implements AccountTrigger {
    private Fragment mCurFragment;
    private Fragment mLoginFragment;
    private Fragment mRegisterFragment;

    @BindView(R.id.img_back)
    ImageView mBack;

    public static void show(Context context) {
        ((MainActivity)context).startActivityForResult(new Intent(context, AccountActivity.class),1);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mCurFragment = mLoginFragment = new LoginFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();
    }

    //在fragment里面切换时回调到这儿处理
    @Override
    public void triggerView() {
        Fragment fragment;
        if (mCurFragment == mLoginFragment) {
            if (mRegisterFragment == null) {
                mRegisterFragment = new RegisterFragment();
            }
            fragment = mRegisterFragment;
        } else {
            fragment = mLoginFragment;
        }
        mCurFragment = fragment;
        //切换
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(0);
    }
}
