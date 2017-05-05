package com.example.mvp_test.model.net;

import android.content.Context;
import android.widget.ImageView;

import com.example.mvp_test.I;
import com.example.mvp_test.model.utils.OkImageLoader;


public class ModelBaseImage extends ModelBase implements IModelBaseImage {
    @Override
    public void release() {
        super.release();
        OkImageLoader.release();
    }

    @Override
    public void showImage(Context context, String userName, ImageView imageView, int defaultPicId, boolean isDragging) {
        OkImageLoader.build(I.DOWNLOAD_AVATAR_URL+userName)
                .imageView(imageView)
                .defaultPicture(defaultPicId)
                .setDragging(isDragging)
                .showImage(context);
    }

    @Override
    public void showImage(Context context, String userName, ImageView imageView, int width, int height, int defaultPicId, boolean isDragging) {
        OkImageLoader.build(I.DOWNLOAD_AVATAR_URL+userName)
                .imageView(imageView)
                .width(width)
                .height(height)
                .defaultPicture(defaultPicId)
                .setDragging(isDragging)
                .showImage(context);
    }
}
