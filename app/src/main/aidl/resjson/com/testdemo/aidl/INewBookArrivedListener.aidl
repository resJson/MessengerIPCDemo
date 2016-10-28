// INewBookArrivedListener.aidl
package resjson.com.testdemo.aidl;

// Declare any non-default types here with import statements

import resjson.com.testdemo.aidl.Book;

interface INewBookArrivedListener {

    void onNewBookArrived(in Book book);

}
