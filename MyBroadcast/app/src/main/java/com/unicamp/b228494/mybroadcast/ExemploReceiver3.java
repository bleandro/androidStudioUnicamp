package com.unicamp.b228494.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ExemploReceiver3 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.i("ExemploReceiver3", "Teste ANS sleeping...");
            for(int c = 0; c < 100; c++) {
                Thread.sleep(1000);
                Log.i("ExemploReceiver3", "Agora em..." + c);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
