package com.wuxiankeneng.jian;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.wuxiankeneng.jian.activity.BaseActivityView;
import com.wuxiankeneng.jian.di.module.ActivityModule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
public class LoginActivity extends BaseActivityView<LoginPresenter>
        implements LoginContract.View {

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }



    @Override
    protected void initWidget() {
        super.initWidget();
        findViewById(R.id.click)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        FileOutputStream out;
        BufferedWriter writer;

        try {
            out = openFileOutput("yaasdng", MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write("wosh834ie");
            writer.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mPresenter.login();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void loginSuccess() {
        Log.e("TAGa", "登陆成功");
    }


}
