package com.example.mvp_test.presenter;

import android.content.Context;

import com.example.mvp_test.I;
import com.example.mvp_test.bean.UserBean;
import com.example.mvp_test.model.net.IModelContact;
import com.example.mvp_test.model.net.ModelContact;
import com.example.mvp_test.model.net.OnCompleteListener;
import com.example.mvp_test.view.iview.IViewContact;


/**
 * Created by Administrator on 2017/5/3.
 */

public class PresenterContact extends PresenterBaseImage implements IPresenterContact {
    IModelContact mModel;
    IViewContact<UserBean[]> mView;

    public PresenterContact(IViewContact<UserBean[]> mView) {
        this.mView = mView;
        mModel = new ModelContact();
    }

    @Override
    public void downloadContactList(final Context context, String userName, int pageId, final int action) {
        if (action == I.ACTION_DOWNLOAD) {
            mView.showDialog(context, "loading");

        }
        mModel.downloadContactList(context, userName, pageId, new OnCompleteListener<UserBean[]>() {
            @Override
            public void onSuccess(UserBean[] result) {
                if (action == I.ACTION_DOWNLOAD) {
                    mView.hideDialog(context);
                }
                mView.showResult(result, action);
            }

            @Override
            public void onError(String error) {
                if (action == I.ACTION_DOWNLOAD) {
                    mView.hideDialog(context);
                }
                mView.showError(error);
            }
        });
    }
}
      