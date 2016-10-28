package resjson.com.testdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import resjson.com.testdemo.aidl.Book;
import resjson.com.testdemo.aidl.IBookManager;
import resjson.com.testdemo.aidl.INewBookArrivedListener;

/**
 * aidl IPC通信
 * Created by wl08029 on 2016/10/26.
 */

public class AidlIpcService extends Service {

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<INewBookArrivedListener> mINewBookArrivedListener = new CopyOnWriteArrayList<>();

    private AtomicBoolean isServiceDestoryed = new AtomicBoolean(false);

    private Binder mBinder = new IBookManager.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerLister(INewBookArrivedListener listener) throws RemoteException {
            if(!mINewBookArrivedListener.contains(listener)){
                mINewBookArrivedListener.add(listener);
            }else{
                Toast.makeText(AidlIpcService.this, "listener already is exists",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void unRegistListener(INewBookArrivedListener listener) throws RemoteException {
            if(mINewBookArrivedListener.contains(listener)){
                mINewBookArrivedListener.remove(listener);
            }else{
                Toast.makeText(AidlIpcService.this, "listener not found",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "iOS"));
        new Thread(new ServiceNewBookWorker()).start();
    }

    private class ServiceNewBookWorker implements Runnable{
        @Override
        public void run() {
            while(!isServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new Book" + bookId);
                mBookList.add(newBook);
                for(int i = 0; i < mINewBookArrivedListener.size(); i++){
                    try {
                        mINewBookArrivedListener.get(i).onNewBookArrived(newBook);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        isServiceDestoryed.set(true);
        super.onDestroy();
    }
}
