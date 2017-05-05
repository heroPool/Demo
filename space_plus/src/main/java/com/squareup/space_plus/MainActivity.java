package com.squareup.space_plus;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.space_plus.views.FlowIndicator;


public class MainActivity extends AppCompatActivity {
    FlowIndicator mFlowIndicator;
    int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mFlowIndicator = (FlowIndicator) findViewById(R.id.flowIndicator);
        mCount = mFlowIndicator.getCount();
    }

    public void onClick(View view) {
        new MyAsyncTask().execute();
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            int focus = 0;
            while (true) {
                publishProgress(focus = focus < mCount - 1 ? focus + 1 : 0);
                SystemClock.sleep(2000);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mFlowIndicator.setFocus(values[0]);
        }
    }

}

