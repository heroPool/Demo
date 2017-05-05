package com.squareup.space_plus4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    Double[] mDoubles = new Double[10_000_000];
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    imageView.setImageResource(R.drawable.icon60040);
                    break;
            }
        }
    };

    public void btnsend(View view) {
        Message msg = Message.obtain();
        msg.what = 0;
        handler.sendMessageDelayed(msg, Integer.MAX_VALUE);
    }

    static class MyHandler extends Handler {    //使用静态内部类，弱引用来解决,handler持有mainactivity的弱引用
        WeakReference<MainActivity> reference;
        WeakReference<MainActivity> reference2;


        public MyHandler(MainActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            //获取弱引用引用的MainActivity对象
            MainActivity activity = reference.get();
            if (activity != null) {
                activity.imageView.setImageResource(R.drawable.icon60040);
            }
            reference.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);    //使用清空消息队列的方法来解决
    }
}
