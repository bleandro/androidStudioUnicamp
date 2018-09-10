package com.unicamp.b228494.calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    private Button calcBt;
    private Button listBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calcBt = (Button) findViewById(R.id.calcXML);
        listBt = (Button) findViewById(R.id.l2XML);

        calcBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(Main2Activity.this, MainActivity.class);
                Intent myIntent = new Intent("calculadora");
                startActivity(myIntent);
            }
        });

        listBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Main2Activity.this, Lista.class);
                startActivity(myIntent);
            }
        });
    }

}
