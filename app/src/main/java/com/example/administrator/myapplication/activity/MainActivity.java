package com.example.administrator.myapplication.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.aidl.Book;
import com.example.administrator.myapplication.aidl.IBookManager;
import com.example.administrator.myapplication.bean.MyParcelableBean;
import com.example.administrator.myapplication.service.BookManagerService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button button;
    private Button button_parcelable_object;
    private List<MyParcelableBean> beanList=new ArrayList<>();

    private Button bindServiceButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initClick();

    }

    private void initClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FirstActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) beanList);
                startActivity(intent);
            }
        });

        button_parcelable_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, SecondActivity.class);
                Bundle bundle = new Bundle();
                MyParcelableBean bean = new MyParcelableBean("要",44,false);
                bundle.putParcelable("bean",bean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        bindServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BookManagerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        for (int i = 0; i <3 ; i++) {
            MyParcelableBean bean = new MyParcelableBean("姚仕超",i,true);
            beanList.add(bean);
        }
    }

    private void initView() {
        button = findViewById(R.id.button_parcelable_list);
        button_parcelable_object = findViewById(R.id.button_parcelable_object);
        bindServiceButton =findViewById(R.id.button_bind_service);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
