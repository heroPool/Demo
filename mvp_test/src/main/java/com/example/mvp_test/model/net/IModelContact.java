package com.example.mvp_test.model.net;

import android.content.Context;

import com.example.mvp_test.bean.UserBean;

/**
 * Created by Administrator on 2017/5/3.
 */

public interface IModelContact extends IModelBase {

    void downloadContactList(Context context, String username, int pageid, OnCompleteListener<UserBean[]> listener);
}
