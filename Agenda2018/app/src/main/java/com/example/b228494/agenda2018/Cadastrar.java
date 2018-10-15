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

public class Cadastrar extends AppCompatActivity {
    private Button btnCadastrar;
    private EditText nome;
    private EditText telefone;
    private EditText email;
    private ContatosDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnCadastrar = (Button) findViewById(R.id.cadastraXML);
        nome = (EditText) findViewById(R.id.nomeXML);
        telefone = (EditText) findViewById(R.id.phoneXML);
        email = (EditText) findViewById(R.id.emailXML);

        db = new ContatosDB(this);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nome.getText().length() == 0 || email.getText().length() == 0 || telefone.getText().length() == 0) {
                    Toast.makeText(Cadastrar.this, "Por favor preencha todos os campos.", Toast.LENGTH_LONG).show();
                } else {
                    String name = nome.getText().toString();
                    String mail = email.getText().toString();
                    int phone = Integer.parseInt(telefone.getText().toString());

                    Contato contato = new Contato(0, name, phone, mail);
                    long id = db.salvaContato(contato);

                    if (id != -1)
                        Toast.makeText(Cadastrar.this, "Cadastro Realizado", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Cadastrar.this, "Ops! Não foi possível cadastrar o contato", Toast.LENGTH_LONG).show();

                    nome.setText("");
                    telefone.setText("");
                    email.setText("");
                }
            }
        });
    }

}
