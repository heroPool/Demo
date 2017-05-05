package com.example.okimageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.okimageloader.bean.AppBean;
import com.example.okimageloader.utils.OkImageLoader;
import com.example.okimageloader.utils.OkUtils;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String SERVER_URL = "http://10.0.2.2";
    RecyclerView mrvApp;
    ArrayList<AppBean> mAppList;
    AppAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initData() {
        final OkUtils<AppBean[]> utils = new OkUtils<>(this);
        utils.url(SERVER_URL+"/app.json")
                .targetClass(AppBean[].class)
                .execute(new OkUtils.OnCompleteListener<AppBean[]>() {
                    @Override
                    public void onSuccess(AppBean[] result) {
                        ArrayList<AppBean> appList = utils.array2List(result);
                        mAdapter.addData(appList);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void initView() {
        mrvApp = (RecyclerView) findViewById(R.id.rvApp);
        mAppList = new ArrayList<>();
        mAdapter = new AppAdapter(this, mAppList);
        mrvApp.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrvApp.setLayoutManager(layoutManager);
    }

    class AppHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvName,tvVersion,tvFileSize;

        public AppHolder(View itemView) {
            super(itemView);
            ivThumb = (ImageView) itemView.findViewById(R.id.ivThumb);
            tvName = (TextView) itemView.findViewById(R.id.tvAppName);
            tvVersion = (TextView) itemView.findViewById(R.id.tvVersion);
            tvFileSize = (TextView) itemView.findViewById(R.id.tvFileSize);

        }
    }


    class AppAdapter extends RecyclerView.Adapter<AppHolder> {
        Context context;
        ArrayList<AppBean> appList;
        ViewGroup parent;

        public AppAdapter(Context context, ArrayList<AppBean> appList) {
            this.context = context;
            this.appList = appList;
        }

        public void addData(ArrayList<AppBean> appList) {
            this.appList.addAll(appList);
            notifyDataSetChanged();
        }

        @Override
        public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            this.parent=parent;
            return new AppHolder(View.inflate(context, R.layout.item_app, null));
        }

        @Override
        public void onBindViewHolder(final AppHolder holder, int position) {
            AppBean app = appList.get(position);
            holder.tvName.setText(app.getName());
            holder.tvVersion.setText("版本:"+app.getVersion());
            holder.tvFileSize.setText(app.getFileSize()+"kb");
            //将当前图片的url保存在ImageView中
            holder.ivThumb.setTag(SERVER_URL+app.getThumb());

            OkImageLoader.build(SERVER_URL+app.getThumb())
                    .width(80)
                    .height(80)
                    .listener(new OkImageLoader.OnImageLoadListener() {
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
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }).loadImage(context);
        }

        @Override
        public int getItemCount() {
            return appList.size();
        }
    }
}

