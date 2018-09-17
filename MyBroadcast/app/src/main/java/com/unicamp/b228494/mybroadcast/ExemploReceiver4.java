package com.unicamp.b228494.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ExemploReceiver4 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it = new Intent(context, TelaTeste.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
    }
}
