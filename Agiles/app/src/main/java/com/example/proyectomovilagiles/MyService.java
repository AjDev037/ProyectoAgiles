package com.example.proyectomovilagiles;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.jetbrains.annotations.NotNull;

import objetos.Observer;

public class MyService extends Service implements Observer {


    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static String CHANNEL_ID2 = "CLASE PROXIMA";

    private final static int NOTIFICACION_ID = 0;
    Comparacion hilo;
    //ComparacionCalendarizada cc;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
        DbHandler db = new DbHandler(getApplicationContext());
        hilo = new Comparacion(this,db);

        //cc = new ComparacionCalendarizada(this, db);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Estoy en el servicio");
        new Thread(hilo).start();

        //cc.calendarizar();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void notificar(@NotNull String name) {
        System.out.println("Me Notificaron");
        notificacionChannel();
        //String extension = textfile.split("\\.")[1];
        String clase = name.split("\\.")[0];
        String hora = name.split("\\.")[1];
        //String codigo = name.split(".").toTypedArray();
        //println("VOY A IMPRIMIR")
        //var materia = codigo[0]
        //var clase = codigo[1]
        //var estado = codigo[2]
        //println(materia)
        //println(clase)
        //println(estado)
        crearNotificacion(clase, hora);
    }

    public void crearNotificacion(String str,String hora){
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        notificacion.setSmallIcon(R.mipmap.ic_launcher);
        notificacion.setTicker("Notificacion por tiempo");
        notificacion.setWhen(System.currentTimeMillis());
        notificacion.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificacion.setContentTitle(str);
        notificacion.setContentText("Tienes una clase pronto a las: " + hora);

        NotificationManagerCompat nm = NotificationManagerCompat.from(getApplicationContext());
        nm.notify(NOTIFICACION_ID, notificacion.build());
    }

    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = String.valueOf(NOTIFICACION_ID);
        String channelName = CHANNEL_ID;
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_NONE)
                .setCategory(Notification.CATEGORY_EVENT)
                .build();
        startForeground(1, notification);
    }

    public void notificacionChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Clase";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}


