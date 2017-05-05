package com.example.okimageloader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkImageLoader2 {
    private static final int DOWNLOAD_SUCCESS=0;
    private static final int DOWNLOAD_ERROR=1;

    private static OkHttpClient mOkHttpClient;
    private static Handler mHandler;

    OnImageLoadListener mListener;

    ImageBean mBean;

    /**
     * 动态管理集合中的数据(图片)，按最少使用和时间最久的原则来删除集合中的数据。
     */
    private static LruCache<String,Bitmap> mCaches;

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
        //保存至sd卡的文件名
        String saveFileName;
    }

    private OkImageLoader2(String url) {
        mBean=new ImageBean();
        mBean.url=url;
        if (mOkHttpClient == null) {
            synchronized (OkImageLoader2.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient=new OkHttpClient();
                    initHandler();
                    initCaches();
                }
            }
        }
    }

    private void initCaches() {
        //获取app的内存容量，单位：byte
        long maxMemory = Runtime.getRuntime().maxMemory();
        mCaches = new LruCache<>((int) (maxMemory / 4));
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

    public static OkImageLoader2 build(String url) {
        return new OkImageLoader2(url);
    }

    public OkImageLoader2 width(int width) {
        mBean.width=width;
        return this;
    }

    public OkImageLoader2 height(int height) {
        mBean.height=height;
        return this;
    }

    /**
     * 预存程序员写的图片下载结束后的处理代码
     * @param listener
     * @return
     */
    public OkImageLoader2 listener(OnImageLoadListener listener) {
        mListener=listener;
        return this;
    }

    /**
     * 加载图片
     * @param context
     */
    public Bitmap loadImage(Context context) {
        if (mBean.url == null) {
            Message msg = Message.obtain();
            msg.what=DOWNLOAD_ERROR;
            msg.obj = "没有设置url";
            mHandler.sendMessage(msg);
            return null;
        }
        if (mCaches.get(mBean.url) != null) {
            Bitmap bitmap = mCaches.get(mBean.url);
            return bitmap;
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
                    mCaches.put(mBean.url, bitmap);
                    mBean.bitmap=bitmap;
                    Message msg = Message.obtain();
                    msg.what=DOWNLOAD_SUCCESS;
                    msg.obj=mBean;
                    mHandler.sendMessage(msg);
                }
            }
        });
        return null;
    }
}
