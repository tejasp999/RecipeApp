/*Assignment : Inclass08
Yash Ghia
Prabhakar Teja Seeda*/

package com.example.teja.inclass08;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yash_ on 10/31/2017.
 */

class GetRecipeAsyncTask extends AsyncTask<String, Integer ,ArrayList<RecipeResults>>  {
    Activity context;
    ArrayList<RecipeResults> Results = new ArrayList<>();
    int totalRecipeCount;
    ProgressBar progressBar;
    IrecipeResponse recipesResponse;
    InoResults noResults;

    public GetRecipeAsyncTask(Activity context, IrecipeResponse recipesResponse, InoResults noResults){
        this.context = context;
        this.recipesResponse = recipesResponse;
        this.noResults =noResults;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = (ProgressBar) context.findViewById(R.id.determinateBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onPostExecute(ArrayList<RecipeResults> recipeResults) {
        Results = recipeResults;
        totalRecipeCount = recipeResults.size();
        super.onPostExecute(recipeResults);
        progressBar.setVisibility(progressBar.INVISIBLE);

        if (totalRecipeCount>0) {
            recipesResponse.recipes(Results);
        }else {
            Log.d("response","No results");
            noResults.noResults();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    @Override
    protected ArrayList<RecipeResults> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int statusCode = connection.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = reader.readLine();
                while(line!=null){
                    stringBuilder.append(line);
                    line = reader.readLine();
                }
                return parseRecipeResult(stringBuilder.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<RecipeResults> parseRecipeResult(String in) throws JSONException {
        ArrayList<RecipeResults> recipeResult = new ArrayList<>();
        JSONObject rootObject = new JSONObject(in);
        JSONArray recipeJSONArray = rootObject.getJSONArray("results");
        for(int i=0;i<recipeJSONArray.length();i++){
            JSONObject recipeJSONObject = recipeJSONArray.getJSONObject(i);
            recipeResult.add(RecipeResults.createResults(recipeJSONObject));
            publishProgress(i*100/recipeJSONArray.length());
        }
        return recipeResult;
    }

    interface IrecipeResponse{
        void recipes(ArrayList<RecipeResults> recipeResults ) ;
    }

    interface InoResults{
        void noResults();
    }
}
