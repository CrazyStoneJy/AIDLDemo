package me.crazystone.study.messengerserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by crazy_stone on 18-2-26.
 */

public class MessengerService extends Service {

    static final int FLAG = 0X11;
    Messenger messenger = new Messenger(new MyHandler());

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    public static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Message messageClient = Message.obtain(msg);
            switch (messageClient.what) {
                case FLAG:
                    int a = messageClient.arg1;
                    int b = messageClient.arg2;
                    messageClient.arg2 = a + b;
                    try {
                        Thread.sleep(2000);
                        messageClient.replyTo.send(messageClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
