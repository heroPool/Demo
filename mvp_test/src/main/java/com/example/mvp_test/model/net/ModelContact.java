package com.example.mvp_test.model.net;

import android.content.Context;

import com.example.mvp_test.I;
import com.example.mvp_test.bean.UserBean;
import com.example.mvp_test.model.utils.OkUtils;

/**
 * Created by Administrator on 2017/5/3.
 */

public class ModelContact extends ModelBase implements IModelContact {
    @Override
    public void downloadContactList(Context context, String username, int pageid, OnCompleteListener<UserBean[]> listener) {
        OkUtils<UserBean[]> utils = new OkUtils<>(context);
        utils.url(I.SERVER_URL)
                .addParam(I.KEY_REQUEST, I.REQUEST_DOWNLOAD_CONTACT_LIST)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.PAGE_ID, pageid + "")
                .addParam(I.KEY_PAGE_SIZE, I.PAGE_SIZE_DEFAULT + "")
                .targetClass(UserBean[].class)
                .execute(listener);
    }
}
