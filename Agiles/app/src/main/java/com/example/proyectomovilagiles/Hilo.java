package com.example.proyectomovilagiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import objetos.Observer;

public class Hilo implements Runnable {

    Observer obs;

    public Hilo(Observer obs){
        this.obs = obs;
    }

    @Override
    public void run() {
        while (true){
            if(getHoraActual().equals("17:50") || getHoraActual().equals("5:50")){
                obs.notificar("Hilo");
            }
        }

    }

    private String getHoraActual() {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormateada = fechaActual.format(dt);



        return horaFormateada;
    }
}
