package resjson.com.testdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

/**
 * Created by wl08029 on 2016/10/11.
 */

public class MessengerIPCService extends Service{

    private Messenger mMessenger = new Messenger(new MessengerHandler());

    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    Toast.makeText(MessengerIPCService.this, msg.getData().getString("msg"), Toast.LENGTH_SHORT).show();
                    Messenger client = msg.replyTo;
                    Message message = Message.obtain(null, 3);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "leilei,ok");
                    message.setData(bundle);
                    try {
                        client.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
