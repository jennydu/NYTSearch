package com.codepath.nytimessearch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jenniferdu on 6/20/16.
 */
public class Article implements Serializable{

    String webUrl;
    String headline;
    String thumbnail;

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }


    public Article(JSONObject jsonObject){
        try {

            this.webUrl = jsonObject.optString("web_url");
            if (webUrl == null || webUrl.length() < 1) {
                this.webUrl = jsonObject.optString("url");
            }

            JSONObject headlineObj = jsonObject.optJSONObject("headline");
            if (headlineObj == null) {
                this.headline = jsonObject.optString("title");
            } else {
                this.headline = headlineObj.getString("main");
            }


            if (jsonObject.optString("multimedia").equals("")) {
                this.thumbnail = "";
            }
            else{
                JSONArray multimedia = jsonObject.optJSONArray("multimedia");
                if (multimedia != null && multimedia.length() > 0 ) {
                    JSONObject multimediaJson = multimedia.optJSONObject(0);
                    if (multimediaJson.getString("url").startsWith("http")) {
                        this.thumbnail = multimediaJson.getString("url");
                    } else {
                        this.thumbnail = "http://nytimes.com/" + multimediaJson.getString("url");

                    }
                }
                else { // if multimedia is null or empty
                    this.thumbnail = "";

                }
            }
            } catch(JSONException e){
                Log.d("error", e.toString());
            }


    }

    public static ArrayList<Article> fromJsonArray(JSONArray array){
        ArrayList<Article> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++){
            try{
                results.add(new Article(array.getJSONObject(i)));
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        return results;
    }


}
