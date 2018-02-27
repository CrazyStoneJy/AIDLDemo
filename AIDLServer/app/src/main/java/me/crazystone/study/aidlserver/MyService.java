package me.crazystone.study.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.crazystone.study.aidlserver.aidl.IMyService;
import me.crazystone.study.aidlserver.aidl.Student;

/**
 * Created by crazy_stone on 18-2-26.
 */

public class MyService extends Service {

    String TAG = MyService.class.getSimpleName();
    String PACKAGE_SAYHI = "me.crazystone.study.aidlserver";
    List<Student> list = new ArrayList<>();

    Binder binder = new IMyService.Stub() {

        @Override
        public List<Student> getStudents() throws RemoteException {
            synchronized (list) {
                Log.d(TAG, "MyService getStudents");
                return list;
            }
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            synchronized (list) {
//                if (!list.contains(student)) {
                list.add(student);
//                }
            }
        }

        // 如果返回为false则表示绑定该service该service失败,否则默认返回super方法,可以让任何包访问,主要用于权限设置.
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

            String packageName = null;
            String[] packages = MyService.this.getPackageManager().
                    getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.d(TAG, "onTransact: " + packageName);
//            if (!PACKAGE_SAYHI.equals(packageName)) {
//                return false;
//            }
//            return true;
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public void onCreate() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "is running");
                }
            }
        };
        thread.start();
//        synchronized (list) {
//            for (int i = 0; i < 6; i++) {
//                Student student = new Student();
//                student.name = "student" + i;
//                student.age = i;
//                list.add(student);
//            }
//        }
        Log.d(TAG, "service onCreate");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


}
