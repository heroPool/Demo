package com.example.mvp_test.model.net;


import com.example.mvp_test.model.utils.OkUtils;

public class ModelBase implements IModelBase {
    @Override
    public void release() {
        OkUtils.release();
    }
}
