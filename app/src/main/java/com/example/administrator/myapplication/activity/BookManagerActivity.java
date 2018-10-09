package com.example.administrator.myapplication.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.aidl.Book;
import com.example.administrator.myapplication.aidl.IBookManager;
import com.example.administrator.myapplication.aidl.IOnNewBookArrivedListener;
import com.example.administrator.myapplication.service.BookManagerService;

import java.lang.ref.WeakReference;
import java.util.List;

public class BookManagerActivity extends AppCompatActivity {
    private static final String TAG = "BookManagerActivity";
    private ServiceConnection mConnection;
    private static final int MESSAGE_NEW_BOOK_ARRIVED=1;
    private MyHandler mHandler=new MyHandler(BookManagerActivity.this);
    private IBookManager mRemoteBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);

        initData();
        Intent intent = new Intent(BookManagerActivity.this, BookManagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

    }

    private void initData() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IBookManager bookManager = IBookManager.Stub.asInterface(service);
                mRemoteBookManager =bookManager;
                try {
                    List<Book> list = bookManager.getBookList();
                    Log.e(TAG, "onServiceConnected: "+list.toString() );
                    bookManager.registerListener(mOnNewBookArrivedListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mRemoteBookManager =null;
            }
        };
    }

    private IOnNewBookArrivedListener mOnNewBookArrivedListener =new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrvied(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,newBook).sendToTarget();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRemoteBookManager !=null && mRemoteBookManager.asBinder().isBinderAlive()){
            try {
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
    }


    static class MyHandler extends Handler {
        WeakReference<BookManagerActivity> weakReference;

        public MyHandler(BookManagerActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BookManagerActivity activity = weakReference.get();
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.e(TAG, "handleMessage: "+msg.obj );
                    break;
            }
        }
    }

}
