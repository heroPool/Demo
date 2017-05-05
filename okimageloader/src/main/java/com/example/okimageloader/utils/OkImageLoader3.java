package com.example.okimageloader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yao on 2017/5/5.
 */

public class OkImageLoader3 {
    private static final int DOWNLOAD_SUCCESS=0;
    private static final int DOWNLOAD_ERROR=1;

    private static OkHttpClient mOkHttpClient;
    private static Handler mHandler;

    OnImageLoadListener mListener;

    ImageBean mBean;

    boolean misDragging;
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
        ImageView imageView;
        int defaultPicId;

    }

    private OkImageLoader3(String url) {
        misDragging=false;
        mBean=new ImageBean();
        mBean.url=url;
        if (mOkHttpClient == null) {
            synchronized (OkImageLoader3.class) {
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
                ImageBean bean= (ImageBean) msg.obj;
                switch (msg.what) {
                    case DOWNLOAD_ERROR:
                        if (bean.listener != null) {
                            bean.listener.onError(msg.obj.toString());
                        }
                        break;
                    case DOWNLOAD_SUCCESS:
                        bean.listener.onSuccess(bean.url,bean.bitmap);
                        break;
                }
            }
        };
    }

    public static OkImageLoader3 build(String url) {
        return new OkImageLoader3(url);
    }

    public OkImageLoader3 width(int width) {
        mBean.width=width;
        return this;
    }

    public OkImageLoader3 height(int height) {
        mBean.height=height;
        return this;
    }

    /**
     * 预存程序员写的图片下载结束后的处理代码
     * @param listener
     * @return
     */
    public OkImageLoader3 listener(OnImageLoadListener listener) {
        mListener=listener;
        return this;
    }

    public OkImageLoader3 listener(final ViewGroup parent) {
        mBean.listener=new OkImageLoader3.OnImageLoadListener() {
            @Override
            public void onSuccess(String url, Bitmap bitmap) {
                ImageView iv = (ImageView) parent.findViewWithTag(url);
                if (iv != null) {// 防止图片错误
                    //显示图片
                    iv.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onError(String error) {

            }
        };
        return this;
    }

    /**
     * 加载图片
     * @param context
     */
    public Bitmap loadImage(final Context context) {
        if (mBean.url == null) {
            Message msg = Message.obtain();
            msg.what=DOWNLOAD_ERROR;
            msg.obj = "没有设置url";
            mHandler.sendMessage(msg);
            return null;
        }
        //从一级缓存中拿图片
        if (mCaches.get(mBean.url) != null) {
            Bitmap bitmap = mCaches.get(mBean.url);
            return bitmap;
        }
        if (mBean.saveFileName != null) {
            String path = FileUtils.getDir(context, mBean.saveFileName);
            Bitmap bitmap = BitmapUtils.getBitmap(path);
            if (bitmap != null) {
                return bitmap;
            }
        }
        if (misDragging) {
            return null;
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
                    //把下载的图片保存至sd卡的Android文件夹下
                    String path = FileUtils.getDir(context, mBean.saveFileName);
                    BitmapUtils.saveBitmap(bitmap,path);
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

    public OkImageLoader3 saveFileName(String saveFileName) {
        this.mBean.saveFileName=saveFileName;
        return this;
    }

    public void showImage(Context context) {
        Bitmap bitmap = loadImage(context);
        if (bitmap != null) {
            mBean.imageView.setImageBitmap(bitmap);
        } else {
            mBean.imageView.setImageResource(mBean.defaultPicId);
        }
    }

    public OkImageLoader3 imageView(ImageView imageView) {
        mBean.imageView=imageView;
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        mBean.width=params.width;
        mBean.height=params.height;
        ViewGroup parent= (ViewGroup) imageView.getParent();
        listener(parent);
        return this;
    }

    public OkImageLoader3 defaultPicId(int defaultPicId) {
        mBean.defaultPicId=defaultPicId;
        return this;
    }

    public OkImageLoader3 setDragging(boolean isDragging) {
        misDragging=isDragging;
        return this;
    }

    public static void release() {
        if (mOkHttpClient != null) {
            mOkHttpClient.dispatcher().cancelAll();;
            mOkHttpClient=null;
            mHandler=null;
            mCaches=null;
        }
    }
}
