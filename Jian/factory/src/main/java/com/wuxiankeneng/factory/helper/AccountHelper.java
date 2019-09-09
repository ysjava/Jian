package com.wuxiankeneng.factory.helper;

import com.wuxiankeneng.common.factory.DataSource;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.R;
import com.wuxiankeneng.factory.card.AddressCard;
import com.wuxiankeneng.factory.db.Student;
import com.wuxiankeneng.factory.model.ResponseModel;
import com.wuxiankeneng.factory.model.account.AccountRspModel;
import com.wuxiankeneng.factory.model.account.LoginModel;
import com.wuxiankeneng.factory.model.account.RegisterModel;
import com.wuxiankeneng.factory.net.Network;
import com.wuxiankeneng.factory.net.RemoteService;
import com.wuxiankeneng.factory.presenter.Account;
import com.wuxiankeneng.factory.presenter.accout.RegisterPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountHelper {
    public static void login(LoginModel model, DataSource.Callback<Student> callback) {
        RemoteService service = Network.remote();
        service.accountLogin(model).enqueue(new AccountRspCallback(callback));
    }

    public static void register(RegisterModel model, DataSource.Callback<Student> callback) {
        RemoteService service = Network.remote();
        service.accountRegister(model).enqueue(new AccountRspCallback(callback));
    }

    public static void bindPush(DataSource.Callback<Student> callback) {
        RemoteService service = Network.remote();
        service.accountBind(Account.getPushId()).enqueue(new AccountRspCallback(callback));
    }


    private static class AccountRspCallback implements Callback<ResponseModel<AccountRspModel>> {
        DataSource.Callback<Student> callback;

        public AccountRspCallback(DataSource.Callback<Student> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<ResponseModel<AccountRspModel>> call, Response<ResponseModel<AccountRspModel>> response) {
            ResponseModel<AccountRspModel> responseModel = response.body();
            if (responseModel == null) {
                callback.onDataNotAvailable(R.string.txt_error_server);
                return;
            }

            if (responseModel.success()) {
                AccountRspModel model = responseModel.getResult();
                Student student = model.getCard().build();
                //保存
                student.saveOrUpdate();

                //同步到xml持续化文件中
                Account.login(model);

                if (model.isBind()) {
                    Account.setBind(true);
                    if (callback != null) {
                        callback.onDataLoaded(student);
                    }
                } else {
                    //唤起绑定
                    bindPush(callback);
                }
            } else {
                if (callback != null)
                    Factory.decodeRspCode(responseModel, callback);
            }
        }

        @Override
        public void onFailure(Call<ResponseModel<AccountRspModel>> call, Throwable t) {
            if (callback != null) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        }
    }

    //地址信息获取
    public static void getAddressList(final DataSource.Callback<List<AddressCard>> callback) {
        RemoteService service = Network.remote();
        service.getAddressList().enqueue(new Callback<ResponseModel<List<AddressCard>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<AddressCard>>> call, Response<ResponseModel<List<AddressCard>>> response) {
                ResponseModel<List<AddressCard>> responseModel = response.body();
                if (responseModel == null) {
                    callback.onDataNotAvailable(R.string.txt_error_server);
                    return;
                }
                if (responseModel.success()) {
                    List<AddressCard> cards = responseModel.getResult();
                    if (callback != null) {
                        callback.onDataLoaded(cards);
                    }
                } else {
                    if (callback != null)
                        Factory.decodeRspCode(responseModel, callback);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<AddressCard>>> call, Throwable t) {
                if (callback!=null)
                    callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
}
