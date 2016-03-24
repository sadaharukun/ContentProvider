package com.example.text1;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        People.CONTENT_URI;
//        this.managedQuery(uri, projection, selection, selectionArgs, sortOrder);
        
       ContentResolver mResolver =  this.getContentResolver();
       
       ContentValues values = new ContentValues();
       values.put(MyUsers.USER_NAME	, "li");
       
       mResolver.insert(MyUsers.CONTENT_URI, values);
//       mResolver.update(uri, values, where, selectionArgs);
       
       Cursor cursor = mResolver.query(MyUsers.CONTENT_URI, new String[]{MyUsers.USER_NAME}, null, null, null);
       cursor.moveToFirst();
       while(cursor.moveToNext()){
    	   
       }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
