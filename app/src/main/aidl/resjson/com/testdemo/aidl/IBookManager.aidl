// IBookManager.aidl
package resjson.com.testdemo.aidl;

// Declare any non-default types here with import statements
import resjson.com.testdemo.aidl.Book;

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);

}
