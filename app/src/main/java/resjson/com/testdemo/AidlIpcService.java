package resjson.com.testdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by wl08029 on 2016/10/26.
 */

public class AidlIpcService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
