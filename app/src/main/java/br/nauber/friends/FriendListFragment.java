package br.nauber.friends;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.List;

/**
 * Created by naubergois on 5/26/16.
 */
public class FriendListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Friend>> {



    private static final String LOG_TAG=FriendListFragment.class.getSimpleName();
    private FriendCustomAdapter mAdapter;
    private static final int LOADER_ID=1;


    private ContentResolver mContentResover;

    private List<Friend> mFriends;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG,"onCreate");
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(LOG_TAG,"onActivityCreate");
        setHasOptionsMenu(true);
        mContentResover=getActivity().getContentResolver();
        mAdapter=new FriendCustomAdapter(getActivity().getBaseContext(),getChildFragmentManager());
        setEmptyText("No friends");
        setListAdapter(mAdapter);
        setListShown(false);
        getLoaderManager().initLoader(LOADER_ID,null,this);


    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG,"onActivityCreateLoader");
        mContentResover=getActivity().getContentResolver();
        return new FriendListLoader(getActivity(),FriendsContract.URI_TABLE,mContentResover);
    }


    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> data) {
        Log.d(LOG_TAG, "onLoadFinished");
        mAdapter.setData(data);
        mFriends=data;
        if(isResumed()){
            Log.d(LOG_TAG, "set List Show");
            setListShown(true);
        }
        else{
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {
        mAdapter.setData(null);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main,menu);
    }
}
