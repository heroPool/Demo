package com.example.mvp_test.presenter;

import android.content.Context;

/**
 * Created by Administrator on 2017/5/3.
 */

public interface IPresenterContact extends IPresenterBaseImage {
    void downloadContactList(Context context, String userName, int pageId, int action);
}
