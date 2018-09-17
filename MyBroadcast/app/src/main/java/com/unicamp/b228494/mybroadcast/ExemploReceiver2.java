package com.unicamp.b228494.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ExemploReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Exemplo Receiver2 - registro dinâmico", Toast.LENGTH_LONG).show();
    }
}
