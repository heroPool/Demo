package com.example.okimageloader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yao on 2017/5/5.
 */

public class OkImageLoader {
    private static final int DOWNLOAD_SUCCESS=0;
    private static final int DOWNLOAD_ERROR=1;

    private static OkHttpClient mOkHttpClient;
    private static Handler mHandler;

    OnImageLoadListener mListener;

    ImageBean mBean;

    public interface OnImageLoadListener {
        void onSuccess(String url, Bitmap bitmap);

        void onError(String error);
    }

    private class ImageBean{
        int width;
        int height;
        String url;
        Bitmap bitmap;
        OnImageLoadListener listener;
        String error;
    }

    private OkImageLoader(String url) {
        mBean=new ImageBean();
        mBean.url=url;
        if (mOkHttpClient == null) {
            synchronized (OkImageLoader.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient=new OkHttpClient();
                    initHandler();
                }
            }
        }
    }

    private void initHandler() {
        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DOWNLOAD_ERROR:
                        if (mListener != null && msg.obj != null) {
                            mListener.onError(msg.obj.toString());
                        }
                        break;
                    case DOWNLOAD_SUCCESS:
                        if (mListener != null && msg.obj != null) {
                            ImageBean bean= (ImageBean) msg.obj;
                            mListener.onSuccess(bean.url,bean.bitmap);
                        }
                        break;
                }
            }
        };
    }

    public static OkImageLoader build(String url) {
        return new OkImageLoader(url);
    }

    public OkImageLoader width(int width) {
        mBean.width=width;
        return this;
    }

    public OkImageLoader height(int height) {
        mBean.height=height;
        return this;
    }

    /**
     * 预存程序员写的图片下载结束后的处理代码
     * @param listener
     * @return
     */
    public OkImageLoader listener(OnImageLoadListener listener) {
        mListener=listener;
        return this;
    }

    /**
     * 加载图片
     * @param context
     */
    public void loadImage(Context context) {
        if (mBean.url == null) {
            Message msg = Message.obtain();
            msg.what=DOWNLOAD_ERROR;
            msg.obj = "没有设置url";
            mHandler.sendMessage(msg);
            return;
        }
        Request request = new Request.Builder().url(mBean.url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what=DOWNLOAD_ERROR;
                msg.obj=e.getMessage();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //存储图片的二进制数组
                byte[] data = response.body().bytes();
                Bitmap bitmap = BitmapUtils.getBitmap(data, mBean.width, mBean.height);
                if (bitmap == null) {
                    Message msg = Message.obtain();
                    msg.what = DOWNLOAD_ERROR;
                    msg.obj = "图片下载失败";
                    mHandler.sendMessage(msg);
                } else {
                    mBean.bitmap=bitmap;
                    Message msg = Message.obtain();
                    msg.what=DOWNLOAD_SUCCESS;
                    msg.obj=mBean;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }
}
