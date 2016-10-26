package resjson.com.testdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MessengerIPCActivity extends Activity {

    private TextView tv_messenger_ipc;

    private Messenger mService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private Messenger mMessenger = new Messenger(new MessengerHandler());

    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 3:
                    Toast.makeText(MessengerIPCActivity.this, msg.getData().getString("reply"), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messenger_ipc);
        Intent intent = new Intent(this, MessengerIPCService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

        tv_messenger_ipc = (TextView) findViewById(R.id.tv_messenger_ipc);
        tv_messenger_ipc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = Message.obtain(null, 2);
                Bundle bundle = new Bundle();
                bundle.putString("msg", "leilei,hello");
                message.setData(bundle);
                message.replyTo = mMessenger;
                try {
                    mService.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
