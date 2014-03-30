package com.example.DBtest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.DBtest.com.nvkudba.recipe.dao.Contributor;
import com.example.DBtest.com.nvkudba.recipe.dao.Plate;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class DBActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    TextView serverData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        serverData = (TextView) findViewById(R.id.serverData);
        new MyTask2().execute();

    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        List<Contributor> contributors;
        @Override
        protected Void doInBackground(Void... params) {
            RestClient.GitHub github = RestClient.getInstance().github;
            try{
                contributors = github.getContributors("square", "retrofit");
                System.out.println("v.done");

            }catch (Exception e){

                System.out.println("v.error");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            System.out.println("v.onPostExecute");
            for (Contributor contributor : contributors) {
                serverData.append("Data: " + contributor.login + " (" + contributor.contributions + ")\n");
            }
            super.onPostExecute(result);
        }

    }

    private class MyTask2 extends AsyncTask<Void, Void, Void> {
        List<Plate> plates;
        @Override
        protected Void doInBackground(Void... params) {
            RestClient.Recipe recipe = RestClient.getInstance().recipe;
            try{
                plates = recipe.getPlates();

            }catch (RetrofitError e){
                try {
                    TypedByteArray body = (TypedByteArray) e.getResponse().getBody();
                    Log.e("dbtest", new String(body.getBytes(),"UTF-8"));
                } catch (UnsupportedEncodingException e1) {
                    Log.e("dbtest","error while tryig to read message in the api error response");
                }
                Log.e("dbtest","Couldn't get recipe data");

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            System.out.println("v.onPostExecute");
            if (plates!=null) {
                for (Plate p : plates) {
                    serverData.append("Data:"+ p.name+ "\n");
                }
            }
            super.onPostExecute(result);
        }

    }
}


