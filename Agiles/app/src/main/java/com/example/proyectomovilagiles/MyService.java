package com.example.proyectomovilagiles;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.jetbrains.annotations.NotNull;

import objetos.Observer;

public class MyService extends Service implements Observer {


    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    Comparacion hilo;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1,new Notification());
        hilo = new Comparacion(this);
        //notificacionChannel();
        //crearNotificacion();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("ESTOY EN EL SERVICIO");
        new Thread(hilo).start();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void notificar(@NotNull String name) {
        System.out.println("ME NOTIFICARON");
        notificacionChannel();
        crearNotificacion(name);
    }

    public void crearNotificacion(String str){
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        notificacion.setSmallIcon(R.mipmap.ic_launcher);
        notificacion.setTicker("Notificacion por tiempo");
        notificacion.setWhen(System.currentTimeMillis());
        notificacion.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificacion.setContentTitle("Se acabo el tiempo");
        notificacion.setContentText(str);

        NotificationManagerCompat nm = NotificationManagerCompat.from(getApplicationContext());
        assert nm != null;
        nm.notify(NOTIFICACION_ID, notificacion.build());
    }

    public void notificacionChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}


