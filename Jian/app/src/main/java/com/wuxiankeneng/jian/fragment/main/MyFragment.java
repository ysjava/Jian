package com.wuxiankeneng.jian.fragment.main;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuxiankeneng.common.app.BaseFragment;
import com.wuxiankeneng.factory.presenter.Account;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.AccountActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class MyFragment extends BaseFragment {
    @BindView(R.id.portrait)
    CircleImageView mPortrait;
    @BindView(R.id.txt_name)
    TextView mName;
    @BindView(R.id.setup)
    ImageView mSetup;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        //是否登陆
        if (Account.isLogin()) {
            //登陆就显示保存到xml文件的信息
            Glide.with(Objects.requireNonNull(getActivity()))
                    .load(Account.getUser().getPortrait())
                    .placeholder(R.drawable.default_portrait)
                    .dontAnimate()
                    .into(mPortrait);
            //设置为账户名字
            mName.setText(Account.getUser().getName());
        } else {
            //没有登陆
            mName.setText("登陆/注册");
        }
    }


    @OnClick({R.id.txt_name, R.id.portrait})
    public void nameAndPortraitOnClick() {
        if (Account.isLogin()) {
            //TODO 已登陆的点击的话就跳转到信息设置界面
            Toast.makeText(getActivity(), "已登陆,跳转设置详情带完成", Toast.LENGTH_SHORT).show();
        } else {
            //没登陆就跳转登陆界面
            AccountActivity.show(Objects.requireNonNull(getActivity()));
        }
    }

}
