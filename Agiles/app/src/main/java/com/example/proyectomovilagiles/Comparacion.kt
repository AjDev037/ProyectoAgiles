package com.example.proyectomovilagiles

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import objetos.Observer


class Comparacion(var obs:Observer, var db : DbHandler): Runnable{

    val observer :Observer = obs
    var bd : DbHandler = db
    var notificado = false;


    override fun run() {
        while (true){
            for( mat in bd.leerDatos()){
                if(mat.dia == getDiaActual()){
                    if(mat.hora == getHoraActual()){
                            observer.notificar(mat.nombre)
                            notificado = true
                    }
                }
                Thread.sleep(3000)
            }
        }
    }



}