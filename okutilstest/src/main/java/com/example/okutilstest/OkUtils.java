package com.example.okutilstest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Anima on 2017/5/4.
 * 登录和注册组合使用
 */

public class OkUtils<T> {
    private static final String UTF_8 = "utf-8";
    private static final int RESULT_OK=0;
    private static final int RESULT_ERROR=1;

    private static OkHttpClient mOkHttpClient;
    private static Handler mHandler;

    private StringBuilder mUrl;

    private OnCompleteListener<T> mListener;

    private Class<T> mClazz;

    public interface OnCompleteListener<T> {
        void onSuccess(T t);

        void onError(String error);
    }

    public OkUtils(Context context) {
        if (mOkHttpClient == null) {
            synchronized (OkUtils.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient=new OkHttpClient();
                    initHandler(context);
                }
            }
        }
    }

    private void initHandler(Context context) {
        mHandler = new Handler(context.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RESULT_ERROR:
                        if (mListener != null && msg.obj != null) {
                            mListener.onError(msg.obj.toString());
                        }
                        break;
                    case RESULT_OK:
                        if (mListener != null && msg.obj != null) {
                            T t= (T) msg.obj;
                            mListener.onSuccess(t);
                        }
                        break;
                }
            }
        };
    }

    public OkUtils<T> url(String url) {
        mUrl = new StringBuilder(url);
        return this;
    }

    public OkUtils<T> addPamar(String key, String value) {
        if (mUrl.indexOf("?") == -1) {
            mUrl.append("?");
        } else {
            mUrl.append("&");
        }
        try {
            mUrl.append(key).append("=").append(URLEncoder.encode(value, UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public OkUtils<T> targetClass(Class<T> clazz) {
        mClazz=clazz;
        return this;
    }

    public void execute(OnCompleteListener<T> listener) {
        mListener=listener;
        if (mUrl == null) {
            Message msg = Message.obtain();
            msg.what=RESULT_ERROR;
            msg.obj = "url不能为空";
            mHandler.sendMessage(msg);
            return;
        }
        if (mUrl.indexOf("?") == -1) {
            Message msg = Message.obtain();
            msg.what=RESULT_ERROR;
            msg.obj = "没有设置请求参数";
            mHandler.sendMessage(msg);
        }
        if (mClazz == null) {
            Message msg = Message.obtain();
            msg.what=RESULT_ERROR;
            msg.obj = "没有设置解析的目标类";
            mHandler.sendMessage(msg);
            return;
        }
        Request request = new Request.Builder().url(mUrl.toString()).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what=RESULT_ERROR;
                msg.obj=e.getMessage();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                T t = new Gson().fromJson(json, mClazz);
                Message msg = Message.obtain();
                msg.what=RESULT_OK;
                msg.obj=t;
                mHandler.sendMessage(msg);
            }
        });
    }
}
