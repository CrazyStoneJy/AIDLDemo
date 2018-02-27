package me.crazystone.study.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.crazystone.study.aidlserver.aidl.IMyService;
import me.crazystone.study.aidlserver.aidl.Student;

public class MainActivity extends AppCompatActivity {

    TextView txt_bind, txt_unbind, txt_revoke;
    String TAG = MainActivity.class.getSimpleName();
    String PACKAGE_NAME = "me.crazystone.study.aidlserver";
    String SERVICE_ACTION_NAME = "me.crazystone.study.aidlserver.MyService";
    IMyService binder = null;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = IMyService.Stub.asInterface(service);
            try {
                Student student = new Student();
                student.name = "jiayan";
                student.age = 25;
                binder.addStudent(student);
                List<Student> list = binder.getStudents();
                Toast.makeText(MainActivity.this, ((list != null && list.size() > 0) ? list.get(0).toString() : ""), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "student:" + ((list != null && list.size() > 0) ? list.get(0) : ""));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "");
            binder = null;
            Toast.makeText(MainActivity.this, "service断开连接", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_bind = findViewById(R.id.txt_bind);
        txt_unbind = findViewById(R.id.txt_unbind);
        txt_revoke = findViewById(R.id.txt_revoke);

//        Intent intent = new Intent(SERVICE_ACTION_NAME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent serviceIntent = new Intent()
                .setComponent(new ComponentName(
                        PACKAGE_NAME,
                        SERVICE_ACTION_NAME));
        txt_bind.setOnClickListener(v -> {
            bindService(serviceIntent, conn, BIND_AUTO_CREATE);
        });
        txt_unbind.setOnClickListener(v -> {
            unbindService(conn);
            binder = null;
            Toast.makeText(MainActivity.this, "解除绑定", Toast.LENGTH_SHORT).show();
        });
        txt_revoke.setOnClickListener(v -> {
            if (binder != null) {
                try {
                    List<Student> students = binder.getStudents();
                    if (students != null && students.size() > 0) {
                        Toast.makeText(MainActivity.this, students.get(0).toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MainActivity.this, "没有绑定", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
