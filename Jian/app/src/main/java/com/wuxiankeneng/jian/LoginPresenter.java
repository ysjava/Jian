package com.wuxiankeneng.jian;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.factory.base.BasePresenter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter {

    private int id;

    @Inject
    public LoginPresenter(int id) {
        this.id = id;
    }

//    @Inject
//    public LoginPresenter(LoginContract.View mView) {
//        super(mView);
//    }

    @Override
    public void login() {
        Log.e("TAGa", "发起登陆:"+id);
        getView().loginSuccess();

    }

    public void save() {
        FileOutputStream out;
        BufferedWriter writer;

        try {
            out = Application.getInstance().openFileOutput("aas001ng", MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write("wo数据4ie");
            writer.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void read() {
        FileInputStream in;
        BufferedReader reader;
        StringBuilder builder = new StringBuilder();
        try {
            in = Application.getInstance().openFileInput("aas001ng");
            reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
