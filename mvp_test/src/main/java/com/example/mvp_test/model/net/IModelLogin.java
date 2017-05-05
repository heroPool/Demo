package com.example.mvp_test.model.net;

import android.content.Context;

import com.example.mvp_test.bean.UserBean;


public interface IModelLogin extends IModelBase {
    void login(Context context, String userName, String password, OnCompleteListener<UserBean> listener);
}
