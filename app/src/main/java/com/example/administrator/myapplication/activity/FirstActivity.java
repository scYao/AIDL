package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.aidl.Book;
import com.example.administrator.myapplication.bean.MyParcelableBean;

import java.util.List;

public class FirstActivity extends AppCompatActivity {
    private static final String TAG = "FirstActivity";
    private List<MyParcelableBean> beanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Intent intent = getIntent();
        beanList = intent.getParcelableArrayListExtra("list");
        Log.e(TAG, "onCreate: "+beanList.toString() );

        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sss","sdd");
        editor.apply();

        String name = sharedPreferences.getString("sss","fjdfjflja");

    }
}
