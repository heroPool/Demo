package com.example.mvp_test.presenter;

import android.content.Context;
import android.widget.ImageView;


public interface IPresenterBaseImage extends IPresenterBase {
    void showImage(Context context, String userName, ImageView imageView, int defaultPicId, boolean isDragging);
    void showImage(Context context, String userName, ImageView imageView, int width, int height, int defaultPicId, boolean isDragging);
}
