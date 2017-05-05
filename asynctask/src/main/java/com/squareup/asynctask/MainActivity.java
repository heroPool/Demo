package com.squareup.asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    static final int REQUEST_LOGIN=0;
    static final int REQUEST_REGISTER=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListener();
        loginGet();
        initView();
        Bundle bundle = new Bundle();
        bundle.putString("name","zhuhuajian");

        listView = (ListView) findViewById(R.id.listView);
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_LOGIN);


        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {

            savedInstanceState.getSerializable("");
        } else {

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putSerializable("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_LOGIN:

                User user = (User) data.getSerializableExtra("user");
                break;
        }
    }

    private void initView() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"1", "2"});
//        ArrayAdapter.createFromResource(this,android.R.layout.simple_list_item_1,)
    }

    private void setListener() {
        new MyAsyncTask().execute();
    }

    class MyAsyncTask extends AsyncTask<String, Integer, Boolean> {
        @Override //后台执行耗时任务，不可以进行UI操作，如果要反馈当前任务进度，可使用publishProgress()来完成
        protected Boolean doInBackground(String... params) {
            publishProgress();
            publishProgress();
            publishProgress();
            publishProgress();

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }

        @Override
        protected void onProgressUpdate(Integer[] values) {
            super.onProgressUpdate(values);
        }

        @Override //后台执行任务之前调用
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }

    public void onClick(View View) {
        //创建请求的构建者对象
        Request.Builder builder = new Request.Builder();
        //创建请求
        Request.Builder url = builder.url("");
        Request build = url.build();
        //创建客户端
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建包含请求的任务
        Call call = okHttpClient.newCall(build);
        //创建任务放入请求队列
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                Gson gson = new Gson();
                User[] users = gson.fromJson(string, User[].class);


            }
        });
        Request.Builder builder1 = new Request.Builder();
        Request build1 = builder1.url("").build();
        OkHttpClient okHttpClient1 = new OkHttpClient();
        okHttpClient1.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    public void initHanlder() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        };
        handler.sendEmptyMessage(1);

        Message obtain = Message.obtain();
        obtain.what = 1;
        obtain.arg1 = 3;
        handler.sendMessage(obtain);

    }

    public void loginGet() {
        UrlUtils urlUtils = new UrlUtils();
        String url = urlUtils.url("").addParam("", "").build();
        Request.Builder url1 = new Request.Builder().url(url);
        Request build = url1.build();

        Call call = new OkHttpClient().newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                Gson gson = new Gson();
                User user = gson.fromJson(string, User.class);
                user.toString();
            }
        });

    }

    public void loginPost() {
        String url = "";
        FormBody.Builder builder = new FormBody.Builder();
        FormBody build = builder.add("", "").build();

        Request build1 = new Request.Builder().url(url).post(build).build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


    }

    class AppAdapter extends BaseAdapter {

        Context context;
        ArrayList<User> appList = new ArrayList<>();

        public AppAdapter(Context context, ArrayList<User> appList) {

            this.context = context;
            this.appList = appList;
        }

        @Override
        public int getCount() {
            return appList.size();
        }

        @Override
        public Object getItem(int position) {
            return appList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            View inflate = View.inflate(context, R.layout.item_content, null);
//            ImageView imageView = (ImageView) inflate.findViewById(R.id.imageView);
//            TextView textView = (TextView) inflate.findViewById(R.id.textName);
//
//            User item = (User) getItem(position);
//            imageView.setImageResource(R.drawable.default_face);
//            textView.setText("朱建建");

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();
                View inflate = View.inflate(context, R.layout.item_content, null);
                holder.imageView = (ImageView) inflate.findViewById(R.id.imageView);

                holder.textView = (TextView) inflate.findViewById(R.id.textName);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();

            }


            holder.imageView.setImageResource(R.drawable.default_face);
            holder.textView.setText("朱华建");
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            TextView textView;
        }
    }


}
