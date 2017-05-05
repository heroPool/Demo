package com.squareup.demo2;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListener();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 3000);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };
        handler.sendEmptyMessage(0);
        Message obtain = Message.obtain();
        obtain.what = 1;
        handler.sendMessage(obtain);
        handler.sendEmptyMessage(2);

    }

    private void setListener() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        Resources resources = getResources();

        String string = resources.getString(R.string.nihao);
        Log.i("main", string);

        final ProgressDialog progressDialog = ProgressDialog.show(this, "加载中", "loading...");
        new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(2000);
                progressDialog.dismiss();

            }
        }.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);


    }

    public void writeData(View view) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor edit = login.edit();
        edit.apply();
        SharedPreferences login1 = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor edit1 = login1.edit();

        SharedPreferences login2 = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor edit2 = login2.edit();
        boolean commit = edit2.clear().commit();
        String name = login.getString("name", "0");

        ContentResolver resolver = getContentResolver();
        Cursor query = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        ContentResolver contentResolver = getContentResolver();
        Cursor query1 = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        int id = query.getInt(query.getColumnIndex(MediaStore.Audio.Media._ID));
        String imagename = query.getString(query.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));

        ContentResolver resolver1 = getContentResolver();
        Cursor query2 = resolver1.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String phonename = query2.getString(query2.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        Cursor query3 = resolver1.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);


    }
}
