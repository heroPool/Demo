package com.example.mvp_test.presenter;

import android.content.Context;


public interface IPresenterLogin extends IPresenterBase {
    void login(Context context, String userName, String password) throws Exception;
}
