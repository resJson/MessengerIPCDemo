package resjson.com.testdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView tv_messenger_ipc;
    private TextView tv_aidl_ipc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Messenger IPC通信
        tv_messenger_ipc = (TextView) findViewById(R.id.tv_messenger_ipc);
        tv_messenger_ipc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MessengerIPCActivity.class);
                startActivity(intent);
            }
        });
        // AIDL IPC通信
        tv_aidl_ipc = (TextView) findViewById(R.id.tv_aidl_ipc);
        tv_aidl_ipc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AIDLIpcActivit.class);
                startActivity(intent);
            }
        });
    }
}
