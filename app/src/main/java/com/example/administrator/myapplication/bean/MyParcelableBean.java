package com.example.administrator.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MyParcelableBean implements Parcelable {

    private String name;
    private int age;
    private boolean isMan;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeByte(this.isMan ? (byte) 1 : (byte) 0);
    }

    public MyParcelableBean() {
    }

    public MyParcelableBean(String name, int age, boolean isMan) {
        this.name = name;
        this.age = age;
        this.isMan = isMan;
    }

    protected MyParcelableBean(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.isMan = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MyParcelableBean> CREATOR = new Parcelable.Creator<MyParcelableBean>() {
        @Override
        public MyParcelableBean createFromParcel(Parcel source) {
            return new MyParcelableBean(source);
        }

        @Override
        public MyParcelableBean[] newArray(int size) {
            return new MyParcelableBean[size];
        }
    };

    @Override
    public String toString() {
        return "MyParcelableBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isMan=" + isMan +
                '}';
    }
}
