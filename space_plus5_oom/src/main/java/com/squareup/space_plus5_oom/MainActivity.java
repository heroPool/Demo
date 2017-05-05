package com.squareup.space_plus5_oom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onChurn(View v) {
        String sb = new String();
        for(int i=1;i<=50_000_000;i++) {
            sb+="abc"+i;
        }
    }

    public void onOOM(View v) {
        ArrayList<Person> list = new ArrayList<>();
        for(int i=1;i<=50_000_000;i++) {
            list.add(new Person("avasldfjasld;faldfjas"+i,2,new Object()));
        }
    }

    class Person{
        String name;
        int height;
        Object obj;

        public Person(String name, int height, Object obj) {
            this.name = name;
            this.height = height;
            this.obj = obj;
        }
    }
}
