package br.nauber.friends;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by naubergois on 5/26/16.
 */
public class FriendsDialog extends DialogFragment {

    private LayoutInflater mLayoutInflater;
    public static final String DIALOG_TYPE = "command";
    public static final String DELETE_RECORD = "deleteRecord";
    public static final String DELETE_DATABASE = "deleteDatabase";
    public static final String CONFIRM_EXIT = "confirmExit";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mLayoutInflater = getActivity().getLayoutInflater();
        final View view = mLayoutInflater.inflate(R.layout.friend_dialogue, null);
        String commmand = getArguments().getString(DIALOG_TYPE);
        if (commmand.equals(DELETE_RECORD)) {
            final int _id = Integer.valueOf(getArguments().getString(FriendsContract.FriendsColumns.FRIENDS_ID));
            String name = getArguments().getString(FriendsContract.FriendsColumns.FRIENDS_NAME);
            TextView popupMessage = (TextView) view.findViewById(R.id.pop_message);
            popupMessage.setText("Are you sure you want to delete " + name + " from your friends list");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentResolver resolver = getActivity().getContentResolver();
                    Uri uri = FriendsContract.Friends.buildFriendUri(String.valueOf(_id));
                    resolver.delete(uri, null, null);
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }else{
            if (commmand.equals(DELETE_DATABASE)){
                TextView  popupMessage=(TextView) view.findViewById(R.id.pop_message);
                popupMessage.setText("Are you sure wish to delete the entire database?");
                builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentResolver resolver = getActivity().getContentResolver();
                        Uri uri = FriendsContract.URI_TABLE;
                        resolver.delete(uri, null, null);
                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

            }
            if (commmand.equals(CONFIRM_EXIT)){
                TextView  popupMessage=(TextView) view.findViewById(R.id.pop_message);
                popupMessage.setText("Are you sure wish to exit withput saving?");
                builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });

            }
        }

        return builder.create();
    }
}
