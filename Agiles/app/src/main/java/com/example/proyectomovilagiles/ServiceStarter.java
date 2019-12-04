package com.example.proyectomovilagiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ServiceStarter extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) || Intent.ACTION_USER_PRESENT.equals(intent.getAction()) || Intent.ACTION_USER_INITIALIZE.equals(intent.getAction())){
            Toast.makeText(context,"ME LA PELAS",Toast.LENGTH_LONG).show();
            Intent i = new Intent(context.getApplicationContext(),MyService.class);
            context.getApplicationContext().startForegroundService(i);
        }

    }
}
