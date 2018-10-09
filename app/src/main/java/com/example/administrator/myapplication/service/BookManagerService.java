package com.example.administrator.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.example.administrator.myapplication.aidl.Book;
import com.example.administrator.myapplication.aidl.IBookManager;
import com.example.administrator.myapplication.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();


    private AtomicBoolean mIsServiceDestoryed  = new AtomicBoolean(false);

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!mListenerList.contains(listener)){
//                mListenerList.add(listener);
//            }
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (mListenerList.contains(listener)){
//                mListenerList.remove(listener);
//            }
            mListenerList.unregister(listener);
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"yao"));
        mBookList.add(new Book(2,"chao"));

        new Thread(new ServiceWorker()).start();
    }

    public BookManagerService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    private class ServiceWorker implements Runnable{
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int bookId = mBookList.size()+1;
                Book newBook = new Book(bookId,"new book#"+bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(Book book) throws RemoteException{
        mBookList.add(book);
//        int size =mListenerList.size();
        int size =mListenerList.beginBroadcast();
        for (int i = 0; i < size; i++) {
//            IOnNewBookArrivedListener listener = mListenerList.get(i);
//            listener.onNewBookArrvied(book);
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener !=null){
                listener.onNewBookArrvied(book);
            }

        }
        mListenerList.finishBroadcast();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestoryed.set(true);
    }
}
