package com.squareup.asynctask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        intent.getParcelableExtra()
    }

    public void on(View view) {
        User user = new User();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user",user);
        setResult(RESULT_OK,intent);
        finish();

    }
}
