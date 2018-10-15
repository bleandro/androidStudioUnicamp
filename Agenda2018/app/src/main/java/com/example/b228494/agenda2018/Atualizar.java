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

public class Atualizar extends AppCompatActivity {
    private Button btnAtualiza;
    private Button btnCarrega;
    private EditText nome;
    private EditText telefone;
    private EditText email;
    private ContatosDB db;
    private Contato contato = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new ContatosDB(this);

        btnAtualiza = (Button) findViewById(R.id.atualizaXML);
        btnCarrega = (Button) findViewById(R.id.carregaXML);
        nome = (EditText) findViewById(R.id.nomeAXML);
        telefone = (EditText) findViewById(R.id.phoneAXML);
        email = (EditText) findViewById(R.id.emailAXML);

        btnCarrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contato = db.pesquisaContato(nome.getText().toString());

                if (contato == null) {
                    Toast.makeText(Atualizar.this, "Contato inexistente", Toast.LENGTH_LONG).show();

                    nome.setText("");
                    telefone.setText("");
                    email.setText("");
                }
                else {
                    nome.setText(contato.getNome());
                    telefone.setText(contato.getTelefone());
                    email.setText(contato.getEmail());
                }
            }
        });

        btnAtualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contato == null) {
                    Toast.makeText(Atualizar.this, "Pesquise por um contato válido para continuar", Toast.LENGTH_LONG).show();
                }
                else {
                    if (nome.getText().length() == 0 || email.getText().length() == 0 || telefone.getText().length() == 0) {
                        Toast.makeText(Atualizar.this, "Por favor preencha todos os campos.", Toast.LENGTH_LONG).show();
                    } else {
                        String _nome = nome.getText().toString();
                        int _telefone = Integer.parseInt(telefone.getText().toString());
                        String _email = email.getText().toString();

                        contato.setNome(_nome);
                        contato.setTelefone(_telefone);
                        contato.setEmail(_email);

                        long count = db.salvaContato(contato);

                        if (count > 0)
                            Toast.makeText(Atualizar.this, "Contato atualizado com sucesso", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Atualizar.this, "Ops! Não foi possível atualizar o contato", Toast.LENGTH_LONG).show();

                        nome.setText("");
                        telefone.setText("");
                        email.setText("");

                        contato = null;
                    }
                }
            }
        });
    }
}
