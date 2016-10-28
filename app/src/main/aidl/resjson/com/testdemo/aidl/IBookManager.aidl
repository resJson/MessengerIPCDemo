// IBookManager.aidl
package resjson.com.testdemo.aidl;

// Declare any non-default types here with import statements
import resjson.com.testdemo.aidl.Book;
import resjson.com.testdemo.aidl.INewBookArrivedListener;

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);
    void registerLister(INewBookArrivedListener listener);
    void unRegistListener(INewBookArrivedListener listener);

}
