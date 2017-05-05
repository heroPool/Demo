package com.squareup.administrator.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity  {
    Button btnA,btnB,btnopenhtml;
    EditText etHtml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                Intent intent=new Intent("com.example.demo.Action_start");
                intent.addCategory("android.intent.category.ALTERNATIVE");
                startActivity(intent);
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("http://"+etHtml.getText().toString()));
                intent.setData(Uri.parse("tel:"+etHtml.getText().toString()));
                startActivity(intent);
            }
        });
        btnopenhtml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://"+etHtml.getText().toString()));

                startActivity(intent);

            }
        });
    }

    private void initView() {
        btnA = (Button) findViewById(R.id.btnOne);
        btnB = (Button) findViewById(R.id.btnTwo);
        btnopenhtml = (Button) findViewById(R.id.btnOpenhtml);
        etHtml = (EditText) findViewById(R.id.etHtml);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.queding:

                break;
            case R.id.quxiao:

                break;
            case R.id.finsh:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
