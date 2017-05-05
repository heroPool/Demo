package com.squareup.space_plus3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Double[] mDoubles = new Double[10_000_000];
    TextView mTextView;
    MyInnerClass myInnerClass;

    static Button mbtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myInnerClass = new MyInnerClass();
        myInnerClass.run();

        initView();
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.tv);
        mbtnLogin = (Button) findViewById(R.id.button);
    }

    public void onClick(View v) {
        Utils utils = Utils.getInstance(this);
        utils.setTextView(mTextView);
    }

    boolean is = true;

    class MyInnerClass {
        int i = 0;

        void run() {
            new Thread() {
                @Override
                public void run() {
                    while (is) {
                        i++;
                    }
                }
            }.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mDoubles = null;
        Utils.release();//工具类引起的内存泄漏
        mbtnLogin = null;//静态变量引起的内存泄漏
        is = false;//内部类引起的内存泄漏


    }
}
