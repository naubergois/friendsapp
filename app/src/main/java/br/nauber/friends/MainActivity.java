package br.nauber.friends;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.v("Main ","onCreate");

        FragmentManager fragmentManager=getSupportFragmentManager();
        if(fragmentManager.findFragmentById(android.R.id.content)==null){
            Log.v("Main ","if content == null");
            FriendListFragment friendListFragment=new FriendListFragment();
            fragmentManager.beginTransaction().add(android.R.id.content,friendListFragment).commit();
        }


        //setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        switch (id){
            case R.id.add:
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
                break;
            case R.id.deleteDatabase:
                FriendsDialog dialog=new FriendsDialog();
                Bundle args=new Bundle();
                args.putString(FriendsDialog.DIALOG_TYPE,FriendsDialog.DELETE_DATABASE);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(),"delete-database");
                break;
            case R.id.search:
                Intent intentSearch=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intentSearch);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
}
