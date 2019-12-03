package com.example.proyectomovilagiles;

import objetos.Observer;

public class Hilo implements Runnable {

    Observer obs;

    public Hilo(Observer obs){
        this.obs = obs;
    }

    @Override
    public void run() {
        for(int i= 0; i<= 15;i++){

            try {
                System.out.println("ME DORMI");

                Thread.sleep(1000);
                System.out.println("ESTOY IMPRIMIENDO EL NUMERO: " + i);
                if(i == 15){
                    obs.notificar("Hilo");
                    System.out.println("YA NO VOY A CONTAR, QUE HUEVA");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //createNotification();


        }
    }
}
