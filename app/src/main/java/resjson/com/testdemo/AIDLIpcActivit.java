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
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import resjson.com.testdemo.aidl.Book;
import resjson.com.testdemo.aidl.IBookManager;
import resjson.com.testdemo.aidl.INewBookArrivedListener;

/**
 * aidl IPC通信
 * Created by wl08029 on 2016/10/26.
 */

public class AIDLIpcActivit extends Activity {

    public TextView tv_aidl_ipc;
    public TextView tv_aidl_add_ipc;

    private IBookManager bookManager;

    private List<Book> mBookList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aidl_ipc);

        Intent intent = new Intent(AIDLIpcActivit.this, AidlIpcService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        tv_aidl_ipc = (TextView) findViewById(R.id.tv_aidl_ipc);
        tv_aidl_ipc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast.makeText(AIDLIpcActivit.this, getShowText(bookManager.getBookList()), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        tv_aidl_add_ipc = (TextView) findViewById(R.id.tv_aidl_add_ipc);
        tv_aidl_add_ipc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book(3, "PHP");
                try {
                    bookManager.addBook(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String getShowText(List<Book> list) {
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                str += list.get(i).toString();
            } else {
                str += list.get(i).toString() + "\n";
            }
        }
        return str;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(AIDLIpcActivit.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private INewBookArrivedListener mNewBookArrivedListener = new INewBookArrivedListener.Stub() {

        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            mHandler.obtainMessage(1, book).sendToTarget();
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            bookManager = IBookManager.Stub.asInterface(iBinder);
            try {
                mBookList = bookManager.getBookList();
                bookManager.registerLister(mNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onDestroy() {
        if(bookManager != null && bookManager.asBinder().isBinderAlive()){
            try {
                bookManager.unRegistListener(mNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
