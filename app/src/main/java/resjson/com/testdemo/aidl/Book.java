package resjson.com.testdemo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by wl08029 on 2016/10/26.
 */

public class Book implements Parcelable {

    private String mBookName;
    private int mBookId;

    protected Book(Parcel in) {
        mBookId = in.readInt();
        mBookName = in.readString();
    }

    public Book(int mBookId, String mBookName){
        this.mBookId = mBookId;
        this.mBookName = mBookName;
    }

    @Override
    public String toString() {
        return mBookName + "--" + mBookId;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mBookId);
        parcel.writeString(mBookName);
    }

}
