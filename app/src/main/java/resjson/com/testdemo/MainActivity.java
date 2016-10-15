package resjson.com.testdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView tv_ipc;
    private String str = "童信息：\r\n酒店房间—占床位的儿童需按照成人安排。\r\n酒店早餐—身";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_ipc = (TextView) findViewById(R.id.tv_ipc);
        tv_ipc.setText(str);
        tv_ipc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
