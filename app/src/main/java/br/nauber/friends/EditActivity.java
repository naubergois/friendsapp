package br.nauber.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by naubergois on 5/26/16.
 */
public class EditActivity extends FragmentActivity {

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

        contentResolver=EditActivity.this.getContentResolver();

        Intent intent=getIntent();
        final String _id=intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_ID);
        String name=intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_NAME);
        String phone=intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_PHONE);
        String email=intent.getStringExtra(FriendsContract.FriendsColumns.FRIENDS_EMAIL);

        nameTextView.setText(name);
        emailTextView.setText(email);
        phoneTextView.setText(phone);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()){
                    ContentValues values=new ContentValues();
                    values.put(FriendsContract.FriendsColumns.FRIENDS_NAME,nameTextView.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_EMAIL,emailTextView.getText().toString());
                    values.put(FriendsContract.FriendsColumns.FRIENDS_PHONE,phoneTextView.getText().toString());
                    Uri uri=FriendsContract.Friends.buildFriendUri(_id);
                    int returned=contentResolver.update(uri,values,null,null);
                    Log.d(LOG_TAG, "record returned "+returned);
                    Intent intent=new Intent(EditActivity.this,MainActivity.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }
}
