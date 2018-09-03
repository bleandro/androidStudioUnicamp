package com.unicamp.b228494.calculadora;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText opr1;
    private EditText opr2;
    private Button soma;
    private Button sub;
    private Button mult;
    private Button div;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        opr1 = (EditText) findViewById(R.id.opr1XML);
        opr2 = (EditText) findViewById(R.id.opr2XML);

        opr1.setGravity(Gravity.RIGHT);
        opr2.setGravity(Gravity.RIGHT);

        soma = (Button) findViewById(R.id.somaXML);
        sub = (Button) findViewById(R.id.subXML);
        mult = (Button) findViewById(R.id.multXML);
        div = (Button) findViewById(R.id.divXML);

        result = (TextView) findViewById(R.id.resultXML);

        /*View.OnClickListener hand = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double result_in = 0;

                String lstErro = validaCampos();
                if (lstErro.equals("")) {
                    double opr1_in = Double.parseDouble(opr1.getText().toString());
                    double opr2_in = Double.parseDouble(opr2.getText().toString());

                    switch (v.getId()) {
                        case R.id.somaXML:
                            result_in = opr1_in + opr2_in;
                            break;

                        case R.id.subXML:
                            result_in = opr1_in - opr2_in;
                            break;

                        case R.id.multXML:
                            result_in = opr1_in * opr2_in;
                            break;

                        case R.id.divXML:
                            result_in = opr1_in / opr2_in;
                            break;
                    }

                    result.setText(Double.toString(result_in));

                    //Close keyboard
                    InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                } else
                    Toast.makeText(MainActivity.this, lstErro, Toast.LENGTH_LONG).show();
            }
        };

        soma.setOnClickListener(hand);
        sub.setOnClickListener(hand);
        mult.setOnClickListener(hand);
        div.setOnClickListener(hand);*/
    }

    public void disparo(View v) {
        double result_in = 0;

        String lstErro = validaCampos();
        if (lstErro.equals("")) {
            double opr1_in = Double.parseDouble(opr1.getText().toString());
            double opr2_in = Double.parseDouble(opr2.getText().toString());

            switch (v.getId()) {
                case R.id.somaXML:
                    result_in = opr1_in + opr2_in;
                    break;

                case R.id.subXML:
                    result_in = opr1_in - opr2_in;
                    break;

                case R.id.multXML:
                    result_in = opr1_in * opr2_in;
                    break;

                case R.id.divXML:
                    result_in = opr1_in / opr2_in;
                    break;
            }

            result.setText(Double.toString(result_in));

            //Close keyboard
            InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
        } else
            Toast.makeText(MainActivity.this, lstErro, Toast.LENGTH_LONG).show();
    }

    public String validaCampos(){
        //Verifica se os campos estão preenchidos
        if (opr1.getText().length() == 0 || opr2.getText().length() == 0)
            return "Preencha os dois campos";

        return "";
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
