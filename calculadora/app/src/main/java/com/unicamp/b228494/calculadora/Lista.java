package com.unicamp.b228494.calculadora;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lista extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView lvLista;
    private EditText edInput;
    private Button btAdc;
    private ArrayList<String> lista = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.lvLista = (ListView) findViewById(R.id.listXML);
        this.edInput = (EditText) findViewById(R.id.editXML);
        this.btAdc = (Button) findViewById(R.id.adcXML);

//        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
//        lvLista.setAdapter(itemAdapter);

        MyAdapter adapter = new MyAdapter(this, lista);
        lvLista.setAdapter(adapter);

        this.btAdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = edInput.getText().toString();
                if (!item.equals("")) {
                    lista.add(item);
                    lvLista.invalidateViews();
                    edInput.setText("");

                    //Close keyboard
                    InputMethodManager imm = (InputMethodManager) Lista.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Lista.this.getCurrentFocus().getWindowToken(), 0);
                } else
                    Toast.makeText(Lista.this, "Digite algo!", Toast.LENGTH_LONG).show();
            }
        });

        this.lvLista.setOnItemClickListener(this);
        this.lvLista.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        edInput.setText(lista.remove(position));
        lvLista.invalidateViews();
        Toast.makeText(this, "Editando item", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja remover este item?")
                .setTitle("Alerta")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lista.remove(position);
                        lvLista.invalidateViews();
                        Toast.makeText(Lista.this, "Item apagado!", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Não", null)
                .show();

        return true;
    }
}
