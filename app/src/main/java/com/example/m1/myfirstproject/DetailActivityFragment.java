package com.example.m1.myfirstproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


public class DetailActivityFragment extends Fragment {

    public static String title;
    public static String path;
    public static String overview;
    public static String rating;
    public static String date;
    public static String youtube;
    public static String review;
    public static boolean favorite;
    static String id;
    static String youtubes;
    static ArrayList<String> comments;
    private ShareActionProvider shareActionProvider;
    static String API_KEY = "21995beed75871d8c1185db655692d5f\n";

    public DetailActivityFragment() {
    }

    public class YoutubeReviewLoadTask extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... params) {

            try {
                youtubes = new String(String.valueOf(getYoutubeFromId(id)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return youtubes;
        }
    }

     public class ReviewLoadTask extends AsyncTask<Void,Void,ArrayList<String>>
    {
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            try {
                comments=getReviewFromID(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return comments;
        }


        protected void onPostExecute(ArrayList<String> comments)
        {
            for (int i = 0; i < comments.size(); i++) {
            TextView textView1 = (TextView)getView().findViewById(R.id.review);
            textView1.setText(comments.get(i));
                review=comments.get(i);
        }
       }
    }



    public ArrayList<String> getReviewFromID(String ids) throws IOException {
        String reviewId = getActivity().getIntent().getStringExtra("id");
        ArrayList<String> result = new ArrayList<>();
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            String JSONResult;
            try {
                String URLString = null;
                URLString = "http://api.themoviedb.org/3/movie/" + reviewId + "/reviews?api_key=" + API_KEY;
                URL url = new URL(URLString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                JSONResult = stringBuffer.toString();
                try {
                    result.add(getReviewFromJSON(JSONResult));

                } catch (JSONException e) {
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
        return result;
    }

    public String getReviewFromJSON(String JSONStringParam) throws JSONException {
        JSONObject JSONString=new JSONObject(JSONStringParam);
        JSONArray ReviewArray=JSONString.getJSONArray("results");
        ArrayList<String> result=new ArrayList<String>();
        if(ReviewArray.length()==0)
        {
            result.add("No Review Available");
        }
        else {
            for (int i = 0; i < ReviewArray.length(); i++) {
                JSONObject results = ReviewArray.getJSONObject(i);
                return results.getString("content");
            }
        }
        return result.toString();
    }


    public String getYoutubeFromId(String ids) throws IOException {

        String movieId = getActivity().getIntent().getStringExtra("id");
        String result = new String();
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            String JSONResult;
            try {

                String URLString = null;
                URLString = "http://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=" + API_KEY;

                URL url = new URL(URLString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
                JSONResult = stringBuffer.toString();
                try {
                    result = String.valueOf(getYoutubeFromJSON(JSONResult));

                } catch (JSONException e) {
                    result = "No Video Found";

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
        return result;
    }

    public String getYoutubeFromJSON(String JSONStringParam) throws JSONException {
        JSONObject JSONString=new JSONObject(JSONStringParam);
        JSONArray YoutubeArray=JSONString.getJSONArray("results");
        String result="No videos Found";
        JSONObject youtube;
        youtube=YoutubeArray.getJSONObject(0);
        result=youtube.getString("key");
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent=getActivity().getIntent();
        getActivity().setTitle("Movie Details");

        if(intent!=null && intent.hasExtra("id")) {
            YoutubeReviewLoadTask youtubeReviewLoadTask = new YoutubeReviewLoadTask();
          youtubeReviewLoadTask.execute();
            ReviewLoadTask reviewLoadTask = new ReviewLoadTask();
           reviewLoadTask.execute();
        }

        if(intent!=null && intent.hasExtra("original_title"))
        {
            title=intent.getStringExtra("original_title");
            TextView textView=(TextView)rootView.findViewById(R.id.title);
            textView.setText(title);
        }

        if(intent!=null && intent.hasExtra("overview"))
        {
            overview=intent.getStringExtra("overview");
            TextView textView=(TextView)rootView.findViewById(R.id.overview);
            textView.setText(overview);
        }

        if(intent!=null && intent.hasExtra("vote_average"))
        {
            rating=intent.getStringExtra("vote_average");
            TextView textView=(TextView)rootView.findViewById(R.id.rating);
            textView.setText(rating);
        }

        if(intent!=null && intent.hasExtra("release_date"))
        {
            date=intent.getStringExtra("release_date");
            TextView textView=(TextView)rootView.findViewById(R.id.date);
            textView.setText(date);
        }

        if(intent!=null && intent.hasExtra("poster_path"))
        {
            path=intent.getStringExtra("poster_path");
            ImageView imageView=(ImageView)rootView.findViewById(R.id.image1);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+path).into(imageView);
        }

        if(intent!=null && intent.hasExtra("favorite")) {
            favorite=intent.getBooleanExtra("favorite",false);
            Button b=(Button)rootView.findViewById(R.id.favorite);
            if(b.getText().equals("FAVORITE")) {
                b.setText("UNFAVORITE");
                b.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            }
            else {
                b.setText("FAVORITE");
                b.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
            }
        }

        return rootView;
    }



    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    }
    
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater)
    {
        menuInflater.inflate(R.menu.menu_detail,menu);
        MenuItem menuItem=menu.findItem(R.id.share_action);
        shareActionProvider=(ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if(shareActionProvider!=null)
        {
            shareActionProvider.setShareIntent(ShareIntent());
        }
    }

    private Intent ShareIntent()
    {
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Watch this trailer!!!");
        return shareIntent;
    }

}
