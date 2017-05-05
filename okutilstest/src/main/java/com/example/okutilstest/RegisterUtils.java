package com.example.okutilstest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.okutilstest.bean.MessageBean;
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
 * Created by Administrator on 2017/5/4.
 * 1、对OKhttp的二次封装，简化了OKhttp的使用
 * 2、专门用于注册
 */

public class RegisterUtils {

    private static final String UTF_8 = "utf-8";
    private static final int RESULT_OK = 0;
    private static final int RESULT_ERROR = 1;
    //单例，无论多少个请求都使用一个client维护
    private static OkHttpClient mOkHttpClient;

    private static Handler mHandler;
    //地址栏拼接
    StringBuffer mUrl;

    OnCompleteListener mListener;

    public interface OnCompleteListener {
        //处理成功的结果
        void onSuccess(MessageBean msgbean);

        //处理失败的结果
        void onError(String error);
    }

    Context context;

    public RegisterUtils(Context context) {
        //单例模式创建OKhttpclient
        this.context = context;
        if (mOkHttpClient == null) {
            synchronized (RegisterUtils.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient();
                }
            }
        }
        initHandler(context);
    }

    private void initHandler(Context context) {
        mHandler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RESULT_ERROR:
                        if (msg.obj != null && mListener != null) {
                            mListener.onError(msg.obj.toString());
                        }
                        break;
                    case RESULT_OK:
                        if (msg.obj != null && mListener != null) {
                            MessageBean msgbean = (MessageBean) msg.obj;
                            mListener.onSuccess(msgbean);
                        }
                        break;
                }
            }
        };
    }

    public RegisterUtils url(String url) {
        mUrl = new StringBuffer(url);
        return this;
    }

    public RegisterUtils addParam(String key, String value) {
        if (mUrl.indexOf("?") == -1) {
            mUrl.append("?");

        } else {
            mUrl.append("&");
        }
        try {
            mUrl.append(key)
                    .append("=")
                    .append(URLEncoder.encode(value, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        return this;
    }

    public void execute(OnCompleteListener listener) {
        mListener = listener;
        if (mUrl == null) {
            Message msg = Message.obtain();
            msg.what = RESULT_ERROR;
            msg.obj = "url不能为空";
            mHandler.sendMessage(msg);
            return;
        }
        if (mUrl.indexOf("?") == -1) {
            Message msg = Message.obtain();
            msg.what = RESULT_ERROR;
            msg.obj = "请设置请求类型";
            mHandler.sendMessage(msg);
        }
        Request request = new Request.Builder().url(mUrl.toString()).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = RESULT_ERROR;
                msg.obj = e.getMessage();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                MessageBean msgbean = gson.fromJson(json, MessageBean.class);
                Message msg = Message.obtain();
                msg.what = RESULT_OK;
                msg.obj = msgbean;
                mHandler.sendMessage(msg);
            }
        });
    }

    public static void release() {
        if (mOkHttpClient != null) {
            mOkHttpClient.dispatcher().cancelAll();
            mOkHttpClient = null;
        }
    }

}
