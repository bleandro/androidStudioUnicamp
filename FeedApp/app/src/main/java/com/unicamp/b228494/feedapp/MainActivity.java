package com.unicamp.b228494.feedapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    //private TextView xmlTextView;
    private String mFileContents;
    private Button feedButton;
    private ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //xmlTextView = (TextView) findViewById(R.id.textXML);
        feedButton = (Button) findViewById(R.id.btXML);
        myList = (ListView) findViewById(R.id.lvXML);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseApplication parseApplication = new ParseApplication(mFileContents);
                parseApplication.process();

                ArrayAdapter<Application1> arrayAdapter = new ArrayAdapter<Application1>(MainActivity.this, android.R.layout.simple_list_item_1, parseApplication.getApplications());
                myList.setAdapter(arrayAdapter);
            }
        });
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            mFileContents = downloadXMLFile(strings[0]);

            if (mFileContents == null){
                Log.d("DownloadData", "Erro no download");
            }

            return mFileContents;
        }

        private String downloadXMLFile(String theURL) {
            try{
                URL myUrl = new URL(theURL);
                HttpURLConnection myConnection = (HttpURLConnection) myUrl.openConnection();
                int response = myConnection.getResponseCode();
                Log.d("DownloadData", "O código de resposta é: " + response);
                InputStream data = myConnection.getInputStream();
                InputStreamReader caracteres = new InputStreamReader(data);

                int charReader;
                char[] inputBuffer = new char[500];
                StringBuilder tempBuffer = new StringBuilder();
                while (true) {
                    charReader = caracteres.read(inputBuffer);
                    if (charReader <= 0)
                        break;

                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charReader));
                }

                return tempBuffer.toString();

            }catch (IOException e) {
                Log.d("DownloadData", "IO Exception reading data: " + e.getMessage());
            }catch (SecurityException e) {
                Log.d("DownloadData", "Security exception. Needs Permission?");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //xmlTextView.setText(mFileContents);

            super.onPostExecute(s);
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
