package br.nauber.friends;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naubergois on 5/26/16.
 */
public class FriendListLoader extends AsyncTaskLoader<List<Friend>> {


    private static final String TAG = FriendListLoader.class.getSimpleName();

    private List<Friend> mFriends;
    private ContentResolver contentResolver;
    private Cursor mCursor;

    public FriendListLoader(Context context, Uri uri, ContentResolver contentResolver) {
        super(context);

        this.contentResolver = contentResolver;

    }

    @Override
    protected void onReset() {
        onStopLoading();
        if(mCursor!=null){
            mCursor.close();
        }
        mFriends=null;
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void deliverResult(List<Friend> data) {

        if (isReset()) {
            if (data != null) {
                mCursor.close();
                ;
            }
        }
        List<Friend> oldFriends = data;

        if (data == null | data.size() == 0) {
            Log.d(TAG, "No data returned");
        }
        mFriends = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
        if((oldFriends!=null)&&(oldFriends!=mFriends)){
            mCursor.close();
        }
    }


    @Override
    protected void onStartLoading() {
        if (mFriends!=null)
        {
            deliverResult(mFriends);
        }
        if (takeContentChanged()||(mFriends==null)){
            forceLoad();
        }

    }

    @Override
    public List<Friend> loadInBackground() {
        String[] projection = {BaseColumns._ID, FriendsContract.FriendsColumns.FRIENDS_NAME, FriendsContract.FriendsColumns.FRIENDS_PHONE, FriendsContract.FriendsColumns.FRIENDS_EMAIL};
        List<Friend> entries = new ArrayList<Friend>();
        mCursor = contentResolver.query(FriendsContract.URI_TABLE, projection, null, null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    Friend friend = new Friend();
                    friend.setId(mCursor.getInt(mCursor.getColumnIndex(FriendsContract.FriendsColumns.FRIENDS_ID)));
                    friend.setName(mCursor.getString(mCursor.getColumnIndex(FriendsContract.FriendsColumns.FRIENDS_NAME)));
                    friend.setEmail(mCursor.getString(mCursor.getColumnIndex(FriendsContract.FriendsColumns.FRIENDS_EMAIL)));
                    friend.setPhone(mCursor.getString(mCursor.getColumnIndex(FriendsContract.FriendsColumns.FRIENDS_PHONE)));

                    entries.add(friend);

                } while (mCursor.moveToNext());

            }
        }
        return entries;
    }
}
