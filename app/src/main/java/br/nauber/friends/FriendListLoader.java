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
        Log.d(TAG, "Constructor");

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

        Log.d(TAG, "deliver Result");

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

        Log.d(TAG, "onStartLoading");
        if (mFriends!=null)
        {
            Log.d(TAG, "before deliver result");
            deliverResult(mFriends);
        }
        if (takeContentChanged()||(mFriends==null)){

            Log.d(TAG, "before force loading");
            forceLoad();
        }

    }

    @Override
    public List<Friend> loadInBackground() {

        Log.d(TAG, "Load BackGround");
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
        this.mFriends=entries;
        return entries;
    }
}
