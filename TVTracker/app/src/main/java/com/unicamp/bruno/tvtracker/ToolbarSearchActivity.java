package com.unicamp.bruno.tvtracker;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicamp.bruno.tvtracker.Database.DAO.ScreenPlayDAO;
import com.unicamp.bruno.tvtracker.Database.DAO.ScreenPlayDAOImpl;
import com.unicamp.bruno.tvtracker.app.RequestController;
import com.unicamp.bruno.tvtracker.app.ScreenPlay;
import com.unicamp.bruno.tvtracker.app.ScreenPlayList;

import java.io.IOException;

public class ToolbarSearchActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

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

        lvScreenplays.setOnItemClickListener(this);
        lvScreenplays.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchmenuItem = menu.findItem(R.id.menu_toolbarsearch);
        SearchView searchView = (SearchView) mSearchmenuItem.getActionView();
        searchView.setQueryHint("Title to search");
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
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

                            if (screenPlayList.getResponse().equals("True")) {
                                ArrayAdapter<ScreenPlay> arrayAdapter = new ArrayAdapter<ScreenPlay>(ToolbarSearchActivity.this, android.R.layout.simple_list_item_1, screenPlayList.getSearch());
                                lvScreenplays.setAdapter(arrayAdapter);
                            } else {
                                String error = screenPlayList.getAdditionalProperties().get("Error").toString();
                                Toast.makeText(ToolbarSearchActivity.this, error, Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        finally {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ToolbarSearchActivity.this, "Oops! There was a problem during this search", Toast.LENGTH_LONG).show();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ScreenPlay screenPlay = (ScreenPlay) parent.getAdapter().getItem(position);

        Intent intent = new Intent("informations");
        //Intent intent = new Intent(this, ScreenPlayInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("screenPlay", screenPlay);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final ScreenPlay screenPlay = (ScreenPlay) parent.getAdapter().getItem(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Add \"" + screenPlay.getTitle() + "\" as a favorite?")
                .setTitle("Alert")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Add item as a favorite
                        ScreenPlayDAO screenPlayDAO = new ScreenPlayDAOImpl(ToolbarSearchActivity.this);
                        screenPlayDAO.saveScreenPlay(screenPlay);

                        Toast.makeText(ToolbarSearchActivity.this, "\"" + screenPlay.getTitle() + "\" was added as a favorite", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();

        return true;
    }
}