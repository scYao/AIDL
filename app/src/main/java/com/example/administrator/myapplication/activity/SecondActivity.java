package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.MyParcelableBean;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    private MyParcelableBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle  bundle = getIntent().getExtras();
        if (bundle !=null){
            bean =bundle.getParcelable("bean");
            Log.e(TAG, "onCreate: "+bean );
        }
    }
}
