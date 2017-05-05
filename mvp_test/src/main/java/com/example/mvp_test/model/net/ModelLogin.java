package com.example.mvp_test.model.net;

import android.content.Context;

import com.example.mvp_test.bean.UserBean;
import com.example.mvp_test.I;
import com.example.mvp_test.model.utils.OkUtils;


public class ModelLogin extends ModelBase implements IModelLogin {
    @Override
    public void login(Context context, String userName, String password, OnCompleteListener<UserBean> listener) {
        OkUtils<UserBean> utils = new OkUtils<>(context);
        utils.url(I.SERVER_URL)
                .addParam(I.KEY_REQUEST,I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,userName)
                .addParam(I.User.PASSWORD,password)
                .targetClass(UserBean.class)
                .execute(listener);
    }
}
