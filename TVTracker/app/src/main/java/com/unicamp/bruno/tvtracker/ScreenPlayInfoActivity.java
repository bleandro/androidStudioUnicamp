package com.unicamp.bruno.tvtracker;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicamp.bruno.tvtracker.app.RequestController;
import com.unicamp.bruno.tvtracker.app.ScreenPlay;
import com.unicamp.bruno.tvtracker.app.ScreenPlayInfo;

import java.io.IOException;

public class ScreenPlayInfoActivity extends AppCompatActivity {

    private RequestController requestController;

    private TextView textTitle;
    private TextView textInfo;
    private TextView textRating;
    private TextView textPlot;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_play_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestController = RequestController.getInstance();
        ScreenPlay screenPlay = (ScreenPlay) this.getIntent().getExtras().getParcelable("screenPlay");

        textTitle = (TextView) findViewById(R.id.textTitle);
        textInfo = (TextView) findViewById(R.id.textInfo);
        textRating = (TextView) findViewById(R.id.textRating);
        textPlot = (TextView) findViewById(R.id.textPlot);
        imageView = (ImageView) findViewById(R.id.imageView);

        textTitle.setText("");
        textInfo.setText("");
        textRating.setText("");
        textPlot.setText("");

        this.setPosterImage(screenPlay);
        this.getscreenPlayInfo(screenPlay);
    }

    private void setPosterImage(ScreenPlay screenPlay) {
        String url = screenPlay.getPoster();

        // Request a image response from the provided URL.
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        try {
                            imageView.setImageBitmap(response);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                0,
                0,
                ImageView.ScaleType.FIT_CENTER,
                Bitmap.Config.ALPHA_8,
                null
        );

        // Add the request to the RequestQueue.
        requestController.addToRequestQueue(imageRequest);
    }

    private void getscreenPlayInfo(ScreenPlay screenPlay) {
        String url = RequestController.BASE_URL + RequestController.API_KEY + "&plot=full" + "&i=" + screenPlay.getImdbID();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSON from String to Object
                        ScreenPlayInfo screenPlayInfoReq = null;
                        try {
                            screenPlayInfoReq = new ObjectMapper().readValue(response, ScreenPlayInfo.class);

                            if (screenPlayInfoReq.getResponse().equals("True")) {
                                setInformations(screenPlayInfoReq);
                            }
                            else {
                                String error = screenPlayInfoReq.getAdditionalProperties().get("Error").toString();
                                Toast.makeText(ScreenPlayInfoActivity.this, error, Toast.LENGTH_LONG).show();
                            }

                            // progressBar.setVisibility(View.GONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ScreenPlayInfoActivity.this, "Oops! There was a problem during this search", Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Add the request to the RequestQueue.
        requestController.addToRequestQueue(stringRequest);
    }

    private void setInformations(ScreenPlayInfo screenPlayInfo) {
        if (screenPlayInfo!= null) {
            textTitle.setText(screenPlayInfo.getTitle());
            textInfo.setText(screenPlayInfo.getRated() + " | "
                    + screenPlayInfo.getRuntime() + " | "
                    + screenPlayInfo.getGenre() + " | "
                    + screenPlayInfo.getType() + "(" + screenPlayInfo.getYear() + ")");
            textRating.setText(screenPlayInfo.getImdbRating() + "/10 based in " + screenPlayInfo.getImdbVotes() + " votes");
            textPlot.setText(screenPlayInfo.getPlot());
        }
    }

}
