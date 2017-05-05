package com.example.okutilstest;

import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.okutilstest.bean.A;
import com.example.okutilstest.bean.I;
import com.example.okutilstest.bean.MessageBean;
import com.example.okutilstest.bean.OkUtilsforGeneric;
import com.example.okutilstest.bean.OkUtilsforPost;
import com.example.okutilstest.bean.OkUtilsforUpdatefile;
import com.example.okutilstest.bean.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        LoginUtils utils = new LoginUtils(this);
        utils.url(I.SERVER_URL)
                .addParam(I.KEY_REQUEST, I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, "a")
                .addParam(I.User.PASSWORD, "a")
                .execute(new LoginUtils.OnCompleteListener() {
                    @Override
                    public void onSuccess(UserBean user) {
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Log.i("main", user.toString());
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void register(View view) {
        RegisterUtils utils = new RegisterUtils(this);
        utils.url(I.SERVER_URL)
                .addParam(I.KEY_REQUEST, I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME, "zhu1234567890")
                .addParam(I.User.PASSWORD, "123456")
                .execute(new RegisterUtils.OnCompleteListener() {
                    @Override
                    public void onSuccess(MessageBean msgbean) {
                        Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Log.i("main", msgbean.toString());
                        Log.i("main", msgbean.isSuccess() + "");
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void loginpost(View view) {
        OkUtilsforPost<UserBean> utils = new OkUtilsforPost<>(this);

//        LoginUtils utils = new LoginUtils(this);
        utils.url(I.SERVER_URL)
                .post()
                .addParam(I.KEY_REQUEST, I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, "a")
                .addParam(I.User.PASSWORD, "a")
                .execute(new OkUtilsforPost.OnCompleteListener<UserBean>() {
                    @Override
                    public void onSuccess(UserBean userBean) {
                        Log.e("main", "userbean==" + userBean.toString());
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, "登录失败" + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void uploadFile(View view) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(dir, "a.jpg");
        OkUtilsforUpdatefile<MessageBean> utils = new OkUtilsforUpdatefile<>(this);
        utils.url(I.SERVER_URL)
                .addParam(I.KEY_REQUEST, I.REQUEST_UPLOAD_AVATAR)
                .addParam(I.User.USER_NAME, "a")
                .addParam(I.Avatar.AVATAR_TYPE, "user_avatar")
                .addParam(I.Avatar.FILE_NAME, "a.jpg")
                .addFile(file)
                .targetClass(MessageBean.class)
                .execute(new OkUtilsforUpdatefile.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean msg) {
                        if (msg.isSuccess()) {
                            Toast.makeText(MainActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void parseGeneric(View view) {
        final Type type = new TypeToken<A<String>>() {
        }.getType();
        final OkUtilsforGeneric<A<String>> utils = new OkUtilsforGeneric<>(this);
        utils.url("http://10.0.2.2/a.json")
                .doInBackground(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        A<String> a = new Gson().fromJson(json, type);
                        Message msg = Message.obtain();
                        msg.obj = a;
                        utils.sendMessage(msg);
                    }
                })
                .execute(new OkUtilsforGeneric.OnCompleteListener<A<String>>() {
                    @Override
                    public void onSuccess(A<String> a) {
                        Toast.makeText(MainActivity.this, a.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("main", a.toString());
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
