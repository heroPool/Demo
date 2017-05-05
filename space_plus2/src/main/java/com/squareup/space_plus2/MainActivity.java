package com.squareup.space_plus2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    ProgressBar mBar;
    TextView mtvProgress;

    Handler mHandler;

    private static final int DOWNLOAD_START = 0;
    private static final int DOWNLOADING = 1;
    private static final int DOWNLOAD_FINISH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initHandler();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DOWNLOAD_START:
                        Toast.makeText(MainActivity.this, "开始下载", Toast.LENGTH_SHORT).show();
                        break;
                    case DOWNLOADING:
                        mBar.setProgress(msg.arg1);
                        mtvProgress.setText(msg.arg1 + "%");
                        break;
                    case DOWNLOAD_FINISH:
                        Toast.makeText(MainActivity.this, "开始结束", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    private void initView() {
        mBar = (ProgressBar) findViewById(R.id.pb);
        mtvProgress = (TextView) findViewById(R.id.tvProgress);
    }

    public void onSendMessage(View v) {
        new Thread() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    Message msg = Message.obtain();
//                    Message msg = new Message();
                    msg.what = DOWNLOADING;
                    msg.arg1 = i;
                    mHandler.sendMessage(msg);
                    SystemClock.sleep(50);
                }
            }
        }.start();
    }

    class MyRunnable implements Runnable {
        private int progress;

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public MyRunnable() {
            super();

        }

        @Override
        public void run() {
            mBar.setProgress(progress);
            mtvProgress.setText(progress + "%");
        }
    }


    public void onRunnable(View v) {
        new Thread() {
            int i;

            @Override
            public void run() {
                final MyRunnable myRunnable = new MyRunnable();
                for (i = 1; i <= 100; i++) {
                    myRunnable.setProgress(i);
                    runOnUiThread(myRunnable);
                }
            }
        }.start();

    }
}
