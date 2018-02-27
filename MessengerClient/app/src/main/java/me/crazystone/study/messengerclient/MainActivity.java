package me.crazystone.study.messengerclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static final int FLAG = 0X11;
    TextView txt_connect;
    Button btn_connect, btn_send;
    LinearLayout linear_connect;
    Messenger mService = null;
    int index = 1;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            txt_connect.setText("is connect");
            mService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            t("service连接断开");
            mService = null;
            txt_connect.setText("is disconnect");
        }
    };

    Messenger messenger = new Messenger(new MyHandler());

    public void t(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_connect = findViewById(R.id.txt_connect_state);
        btn_connect = findViewById(R.id.btn_connect);
        linear_connect = findViewById(R.id.linear_show);
        btn_send = findViewById(R.id.btn_send);
        btn_connect.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("me.crazystone.study.messengerserver",
                    "me.crazystone.study.messengerserver.MessengerService"));
            bindService(intent, conn, BIND_AUTO_CREATE);
        });
        btn_send.setOnClickListener(v -> {
            try {
                TextView textView = new TextView(MainActivity.this);
                Random random = new Random();
                int b = random.nextInt(100);
                textView.setId(index);
                textView.setText(index + "+" + b + "=");
                Message message = Message.obtain(null, FLAG, index, b);
                if (message == null) {
                    message = new Message();
                    message.what = FLAG;
                    message.arg1 = index;
                    message.arg2 = b;
                }
                // 设置接受返回消息的对象
                message.replyTo = messenger;
                // 向服务器发送消息
                mService.send(message);
                index++;
                linear_connect.addView(textView);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FLAG:
                    // 接收服务器传回来的message
                    int id = msg.arg1;
                    TextView textview = linear_connect.findViewById(id);
                    textview.append("" + msg.arg2);
                    break;
            }
        }
    }
}
