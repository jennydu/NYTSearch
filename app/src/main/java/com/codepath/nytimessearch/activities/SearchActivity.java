package com.codepath.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.codepath.nytimessearch.Article;
import com.codepath.nytimessearch.ArticleArrayAdapter;
import com.codepath.nytimessearch.EndlessScrollListener;
import com.codepath.nytimessearch.Filters;
import com.codepath.nytimessearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    Button btnFilter;

    String query;
    ArrayList<Article> articles;
    Filters myFilter;

    ArticleArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpViews();

        GridView gvResults = (GridView) findViewById(R.id.gvResults);
        // Attach the listener to the AdapterView onCreate
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView



                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        loadHomeArticles();

    }

    public void loadHomeArticles(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/topstories/v1/home.json?";

        RequestParams params = new RequestParams();
        params.put("api-key",
                "0be89842a4dc4f99ba0d5aa314659d4d");


        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;
                try{
                    articleJsonResults = response.getJSONArray("results");

                    //articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJsonArray(articleJsonResults));

                    Log.d("DEBUG", articles.toString());
                } catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter

        query = etQuery.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key",
                "0be89842a4dc4f99ba0d5aa314659d4d");

        params.put("q", query);

        params.put("page", offset);

        if (myFilter!=null){
            String news_desk = myFilter.getNews_desk();
            String sort = myFilter.getSort();
            String begin_date = myFilter.getBegin_date();

            if (news_desk != null && news_desk.length() > 0){
                params.put("fq", ("news_desk:\"" + news_desk + "\""));
            }
            if (sort != null && sort.length() > 0){
                params.put("sort", sort);
            }
            if (begin_date != null && begin_date.length() > 0){
                params.put("begin_date", begin_date);
            }
        }


        Log.d("search activity", url+"?"+params);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;
                try{
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJsonArray(articleJsonResults));

                    Log.d("DEBUG", articles.toString());
                } catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });




    }

    public void setUpViews(){
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);
        btnFilter = (Button) findViewById(R.id.btnFilter);



        // hook up listener for grid clicks
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create intent to display article
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                // get article
                Article article = articles.get(position);
                // pass article into intent
                i.putExtra("article", article);
                // launch activity
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final int FILTER_REQUEST_CODE = 55;

    public void onFilter(View v){
        Intent i = new Intent(getApplicationContext(), FilterActivity.class);

        startActivityForResult(i, FILTER_REQUEST_CODE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){


        if (requestCode == FILTER_REQUEST_CODE){
            if (resultCode == RESULT_OK){

                myFilter = (Filters) data.getSerializableExtra("filters");


                onFilteredSearch();

            }
        }
    }

    public void onFilteredSearch(){ //  take stuff out of myFilter here
        articles.clear();
        query = etQuery.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key",
                "0be89842a4dc4f99ba0d5aa314659d4d");
        params.put("page", 0);
        params.put("q", query);

        if (myFilter != null) {
            String news_desk = myFilter.getNews_desk();
            String sort = myFilter.getSort();
            String begin_date = myFilter.getBegin_date();

            if (news_desk != null && news_desk.length() > 0) {
                params.put("fq", ("news_desk:\"" + news_desk + "\""));
            }
            if (sort != null && sort.length() > 0) {
                params.put("sort", sort);
            }
            if (begin_date != null && begin_date.length() > 0) {
                params.put("begin_date", begin_date);
            }
        }

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;
                try{
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJsonArray(articleJsonResults));

                    Log.d("DEBUG", articles.toString());
                } catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void onArticleSearch(View view) {
        articles.clear();
        String query = etQuery.getText().toString();  // getting input from the plaintext box and converting to string

        //Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG).show();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key",
                "0be89842a4dc4f99ba0d5aa314659d4d");
        params.put("page", 0);
        params.put("q", query);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;
                try{
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJsonArray(articleJsonResults));

                    Log.d("DEBUG", articles.toString());
                } catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

}
