// IOnNewBookArrivedListener.aidl
package com.example.administrator.myapplication.aidl;

// Declare any non-default types here with import statements
import com.example.administrator.myapplication.aidl.Book;

interface IOnNewBookArrivedListener {
  void onNewBookArrvied(in Book newBook);
}
