package com.example.m1.myfirstproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity  {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    public static boolean Tablet=false;
    public static boolean mTwoPane;
    public boolean isTablet(Context context)
    {
        boolean Ssize=((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)==4);
        boolean size=((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)==Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (Ssize||size);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout outer=(RelativeLayout)findViewById(R.id.outer);
    //    FrameLayout container_detail=(FrameLayout)outer.findViewById(R.id.container_detail);
        if(outer.findViewById(R.id.container_detail)!= null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_detail, new DetailActivityFragment()
                        , DETAILFRAGMENT_TAG)
                        .commit();
            }
        }
        else {
            mTwoPane = false;
        }
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
