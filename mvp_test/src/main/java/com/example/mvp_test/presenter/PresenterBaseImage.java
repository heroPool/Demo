package com.example.mvp_test.presenter;

import android.content.Context;
import android.widget.ImageView;

import com.example.mvp_test.model.net.IModelBaseImage;
import com.example.mvp_test.model.net.ModelBaseImage;


public class PresenterBaseImage extends PresenterBase implements IPresenterBaseImage {
    IModelBaseImage mModel;

    public PresenterBaseImage() {
        mModel=new ModelBaseImage();
    }

    @Override
    public void release() {
        mModel.release();
    }

    @Override
    public void showImage(Context context, String userName, ImageView imageView, int defaultPicId, boolean isDragging) {
        mModel.showImage(context,userName,imageView,defaultPicId,isDragging);
    }

    @Override
    public void showImage(Context context, String userName, ImageView imageView, int width, int height, int defaultPicId, boolean isDragging) {
        mModel.showImage(context,userName,imageView,width,height,defaultPicId,isDragging);
    }
}
