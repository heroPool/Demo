package com.example.mvp_test.presenter;


import com.example.mvp_test.model.net.IModelBase;
import com.example.mvp_test.model.net.ModelBase;

public class PresenterBase implements IPresenterBase {
    IModelBase mModel;

    public PresenterBase() {
        mModel=new ModelBase();
    }

    @Override
    public void release() {
        mModel.release();
    }
}
