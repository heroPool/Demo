package com.example.mvp_test.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mvp_test.R;
import com.example.mvp_test.view.fragment.ContactFragment;
import com.example.mvp_test.view.fragment.LoginFragment;
import com.example.mvp_test.view.fragment.ShowAvatarFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.btnLogin:
                ft.replace(R.id.layout_content,new LoginFragment()).commit();
                break;
            case R.id.btnShowAvatar:
                ft.replace(R.id.layout_content,new ShowAvatarFragment()).commit();
                break;
            case R.id.btnContact:
                ft.replace(R.id.layout_content,new ContactFragment()).commit();
                break;
        }
    }
}
