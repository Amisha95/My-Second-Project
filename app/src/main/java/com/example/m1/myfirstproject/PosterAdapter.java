package com.example.m1.myfirstproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by m1 on 5/25/2016.
 */
public class PosterAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<String> array;
    private int width;
    public PosterAdapter(Context c, ArrayList<String> arrayList,int x)
    {
        context=c;
        array=arrayList;
        width=x;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ImageView imageView;
        if(convertView==null)
        {
            imageView=new ImageView(context);
        }
        else
        {
            imageView=(ImageView)convertView;
        }
        Picasso.with(context).load("https://image.tmdb.org/t/p/w185/"+array.get(position))
                .into(imageView);
        return imageView;
    }
}
