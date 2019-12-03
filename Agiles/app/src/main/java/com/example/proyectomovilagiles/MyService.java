package com.example.proyectomovilagiles;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.jetbrains.annotations.NotNull;

import objetos.Observer;

public class MyService extends Service implements Observer {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1,new Notification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("ESTOY EN EL SERVICIO");
        Hilo hilo = new Hilo(this);
        new Thread(hilo).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void notificar(@NotNull String name) {
        System.out.println("ME NOTIFICARON");
    }
}
