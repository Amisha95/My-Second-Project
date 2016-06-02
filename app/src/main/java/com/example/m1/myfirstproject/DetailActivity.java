package com.example.m1.myfirstproject;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.action_settings)
        {
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void favorite(View view)
    {
       Button b=(Button)findViewById(R.id.favorite);
        if(b.getText().equals("FAVORITE"))
        {
            b.setText("UNFAVORITE");
            b.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

            ContentValues contentValues=new ContentValues();
            contentValues.put(MovieProvider.NAME,DetailActivityFragment.path);
            contentValues.put(MovieProvider.OVERVIEW,DetailActivityFragment.overview);
            contentValues.put(MovieProvider.DATE,DetailActivityFragment.date);
            contentValues.put(MovieProvider.TITLE,DetailActivityFragment.title);
            contentValues.put(MovieProvider.YOUTUBE,DetailActivityFragment.youtubes);
            contentValues.put(MovieProvider.RATING,DetailActivityFragment.rating);
            contentValues.put(MovieProvider.REVIEW,DetailActivityFragment.review);
            getContentResolver().insert(MovieProvider.CONTENT_URI,contentValues);


        }
        else
        {
            b.setText("FAVORITE");
            b.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
            getContentResolver().delete(Uri.parse("content://com.example.provider.Movies/movies"), "title=?",
                    new String[]{DetailActivityFragment.title});
        }
    }

    public void trailer(View view)
    {
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com"+"/watch?v="
                +DetailActivityFragment.youtubes));
        startActivity(browserIntent);
    }

}
