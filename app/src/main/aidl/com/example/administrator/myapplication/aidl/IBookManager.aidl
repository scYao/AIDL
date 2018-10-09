// IBookManager.aidl
package com.example.administrator.myapplication.aidl;

// Declare any non-default types here with import statements
import com.example.administrator.myapplication.aidl.Book;
import com.example.administrator.myapplication.aidl.IOnNewBookArrivedListener;

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);

    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
