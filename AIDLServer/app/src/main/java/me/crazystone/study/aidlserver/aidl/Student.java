package me.crazystone.study.aidlserver.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by crazy_stone on 18-2-26.
 */

public class Student implements Parcelable {

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
    public String name;
    public int age;

    public Student() {
    }

    protected Student(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
