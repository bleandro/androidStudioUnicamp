package com.unicamp.b228494.mybroadcast;

import android.app.ListActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {
    private String[] nomes = new String[] {
            "Exemplo1 estático",
            "Exemplo2 dinâmico",
            "Exemplo3 ANR",
            "Exemplo4 Iniciar Tela",
    };
    private ExemploReceiver2 myReceiver = new ExemploReceiver2();

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        switch (position) {
            case 0:
                sendBroadcast(new Intent(this, ExemploReceiver1.class));
                break;

            case 1:
                sendBroadcast(new Intent("Chamar_receiver2"));
                break;

            case 2:
                sendBroadcast(new Intent(this, ExemploReceiver3.class));
                break;

            case 3:
                sendBroadcast(new Intent(this, ExemploReceiver4.class));
                break;
        }
    }

    protected void onCreate(Bundle b) {
        super.onCreate(b);

        registerReceiver(myReceiver, new IntentFilter("Chamar_receiver2"));

        this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);

        super.onDestroy();
    }
}
