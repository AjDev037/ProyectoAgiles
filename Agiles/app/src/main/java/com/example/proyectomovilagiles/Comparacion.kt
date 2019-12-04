package com.example.proyectomovilagiles

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import objetos.Observer


class Comparacion(var obs:Observer, var db : DbHandler): Runnable{

    //Revisa si ya se envio la notificacion una vez, ahorita no sirve de nada
    //pero eso deberia hacer.
    var notificado = false


    override fun run() {
        while (true){
            //Se mete a la bd para leer las materias del usuario.
            for( mat in db.leerDatos()){
                //Compara si el dia de hoy es el dia de alguna de las materias.
                if(mat.dia == getDiaActual()){
                    //En caso de ser verdadero, se compara la hora para ver si es
                    //la hora correcta y mandar la notificacion al usuario.
                    // NOTA: AQUI ES DONDE LA COMPARACION deberia ser de de 15 minutos
                    // DE DIFERENCIA EN LUGAR DE LA MERA HORA>
                    if(mat.hora == getHoraActual()){
                            //Si cumple la condicion manda a notificar al observador para que
                            // envie el mensaje al telefono.
                            obs.notificar(mat.nombre)
                            notificado = true
                    }
                }
                //Hago dormir el thread un ratito para que no este siempre en segundo plano
                //haciendo el run.
                Thread.sleep(20000)
            }
        }
    }



}