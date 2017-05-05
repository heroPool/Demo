package com.example.mvp_test.model.net;

import android.content.Context;
import android.widget.ImageView;



public interface IModelBaseImage extends IModelBase {
    void showImage(Context context, String userName, ImageView imageView, int defaultPicId, boolean isDragging);

    void showImage(Context context, String userName, ImageView imageView, int width, int height, int defaultPicId, boolean isDragging);
}
