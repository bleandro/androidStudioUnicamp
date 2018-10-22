package com.unicamp.bruno.tvtracker;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicamp.bruno.tvtracker.app.RequestController;
import com.unicamp.bruno.tvtracker.app.ScreenPlayList;
import com.unicamp.bruno.tvtracker.app.Search;

import java.io.IOException;

public class ToolbarSearchActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener{

    private String TAG = ToolbarSearchActivity.class.getSimpleName();
    private RequestController requestController;
    private ListView lvScreenplays;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_search);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvScreenplays = (ListView) findViewById(R.id.lvScreenplays);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        requestController = RequestController.getInstance();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchmenuItem = menu.findItem(R.id.menu_toolbarsearch);
        SearchView searchView = (SearchView) mSearchmenuItem.getActionView();
        searchView.setQueryHint("Title to Search");
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //Close keyboard
        InputMethodManager imm = (InputMethodManager) ToolbarSearchActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ToolbarSearchActivity.this.getCurrentFocus().getWindowToken(), 0);

        progressBar.setVisibility(View.VISIBLE);

        String url = RequestController.BASE_URL + RequestController.API_KEY + "&s=" + query.trim();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSON from String to Object
                        ScreenPlayList screenPlayList = null;
                        try {
                            screenPlayList = new ObjectMapper().readValue(response, ScreenPlayList.class);

                            ArrayAdapter<Search> arrayAdapter = new ArrayAdapter<Search>(ToolbarSearchActivity.this, android.R.layout.simple_list_item_1, screenPlayList.getSearch());
                            lvScreenplays.setAdapter(arrayAdapter);

                            progressBar.setVisibility(View.GONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ToolbarSearchActivity.this, "Ops! Ocorreu um problema durante a busca", Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Add the request to the RequestQueue.
        requestController.addToRequestQueue(stringRequest);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //NOTHING TO DO

        return true;
    }
}