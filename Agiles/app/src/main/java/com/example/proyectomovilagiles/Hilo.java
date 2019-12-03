package com.example.proyectomovilagiles;

public class Hilo implements Runnable {
    @Override
    public void run() {
        for(int i= 0; i<= 30;i++){

            try {
                System.out.println("ME DORMI");

                Thread.sleep(4000);
                System.out.println("ESTOY IMPRIMIENDO EL NUMERO: " + i);
                if(i == 30){
                    //obs.notificar("Hilo");
                    System.out.println("YA NO VOY A CONTAR, QUE HUEVA");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //createNotification();


        }
    }
}
