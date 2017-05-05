package com.example.mvp_test.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.mvp_test.bean.UserBean;
import com.example.mvp_test.model.net.IModelLogin;
import com.example.mvp_test.model.net.ModelLogin;
import com.example.mvp_test.model.net.OnCompleteListener;
import com.example.mvp_test.view.iview.IViewLogin;

public class PresenterLogin extends PresenterBase implements IPresenterLogin {
    IModelLogin mModel;

    IViewLogin<UserBean> mView;
    public PresenterLogin(IViewLogin view) {
        mView = view;
        mModel = new ModelLogin();
    }

    @Override
    public void login(final Context context, String userName, String password) throws Exception {
        if (userName == null) {
            throw new Exception("账号不能为空");
        }
        if (password == null) {
            throw new Exception("密码不能为空");
        }
        mModel.login(context, userName, password, new OnCompleteListener<UserBean>() {
            @Override
            public void onSuccess(UserBean result) {
                Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                mView.showResult(result);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                mView.showError(error);

            }
        });
    }
}


