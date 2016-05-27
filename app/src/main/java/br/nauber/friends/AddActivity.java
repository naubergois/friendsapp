package br.nauber.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by naubergois on 5/26/16.
 */
public class AddActivity extends FragmentActivity{

    private static final String LOG_TAG=AddActivity.class.getSimpleName();
    private TextView nameTextView,emailTextView,phoneTextView;
    private Button button;
    private ContentResolver contentResolver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        nameTextView=(TextView) findViewById(R.id.friendName);
        emailTextView=(TextView) findViewById(R.id.friendEmail);
        phoneTextView=(TextView) findViewById(R.id.friendPhone);
        button=(Button) findViewById(R.id.saveButton);

        contentResolver=AddActivity.this.getContentResolver();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()){
                    ContentValues values=new ContentValues();
                    values.put(FriendsContract.FriendsColumns.FRIENDS_NAME,nameTextView.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_EMAIL,emailTextView.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_PHONE,phoneTextView.getText().toString());
                    Uri returned=contentResolver.insert(FriendsContract.URI_TABLE,values);
                    Log.d(LOG_TAG, "record returned "+returned.toString());
                    Intent intent=new Intent(AddActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),"Please! Ensure that your have entered some valid data",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isValid(){
        if (nameTextView.getText().toString().length()==0||emailTextView.getText().toString().length()==0||phoneTextView.getText().length()==0){
            return false;
        }else{
            return  true;
        }
    }


    private boolean someDataEntered(){
        if (nameTextView.getText().toString().length()>0||emailTextView.getText().toString().length()>0||phoneTextView.getText().length()>0){
            return true;
        }else{
            return  false;
        }
    }

    @Override
    public void onBackPressed() {
        if (someDataEntered()){
            FriendsDialog dialog=new FriendsDialog();
            Bundle args=new Bundle();
            args.putString(FriendsDialog.DIALOG_TYPE,FriendsDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(),"confirm-exit");

        }
    }
}
