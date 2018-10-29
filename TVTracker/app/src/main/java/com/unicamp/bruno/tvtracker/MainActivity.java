package com.unicamp.bruno.tvtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unicamp.bruno.tvtracker.Database.DAO.ScreenPlayDAO;
import com.unicamp.bruno.tvtracker.Database.DAO.ScreenPlayDAOImpl;
import com.unicamp.bruno.tvtracker.app.ScreenPlay;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView lvFavorites;
    private TextView textFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvFavorites = (ListView) findViewById(R.id.lvFavorites);
        textFavorites = (TextView) findViewById(R.id.textFavorites);

        lvFavorites.setOnItemClickListener(this);
        lvFavorites.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        if (id == R.id.action_search) {
            Intent intent = new Intent(this, ToolbarSearchActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.updateFavorites();
    }

    private void updateFavorites() {
        ScreenPlayDAO screenPlayDAO = new ScreenPlayDAOImpl(this);
        ArrayList<ScreenPlay> screenPlays = screenPlayDAO.getAll();

        if (screenPlays.size() > 0)
            textFavorites.setVisibility(View.VISIBLE);
        else
            textFavorites.setVisibility(View.INVISIBLE);

        ArrayAdapter<ScreenPlay> arrayAdapter = new ArrayAdapter<ScreenPlay>(MainActivity.this, android.R.layout.simple_list_item_1, screenPlays);
        lvFavorites.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final ScreenPlay screenPlay = (ScreenPlay) parent.getAdapter().getItem(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete \"" + screenPlay.getTitle() + "\" from favorites?")
                .setTitle("Alert")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ScreenPlayDAO screenPlayDAO = new ScreenPlayDAOImpl(MainActivity.this);

                        if (screenPlayDAO.deleteScreenPlay(screenPlay)) {
                            Toast.makeText(MainActivity.this, "\"" + screenPlay.getTitle() + "\" was deleted from favorites", Toast.LENGTH_LONG).show();
                            updateFavorites();
                        }
                        else
                            Toast.makeText(MainActivity.this, "An error occurred attempting to delete", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();

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
}
