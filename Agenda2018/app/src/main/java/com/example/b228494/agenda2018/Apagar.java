package com.example.b228494.agenda2018;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Apagar extends AppCompatActivity {
    private EditText contato;
    private Button btnApaga;
    private ContatosDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apagar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contato = (EditText) findViewById(R.id.nomeApagaXML);
        btnApaga = (Button) findViewById(R.id.btApagaXML);

        btnApaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = db.apagaContato(contato.getText().toString());

                if (count > 0)
                    Toast.makeText(Apagar.this, "Contato apagado com sucesso", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Apagar.this, "Contato inexistente", Toast.LENGTH_LONG).show();

                contato.setText("");
            }
        });
    }

}
