package com.example.proyectomovilagiles

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import objetos.Observer

class Comparacion(var obs:Observer): Runnable{

    val observer :Observer = obs


    override fun run() {
        while (true){
            if(getHoraActual() == "11:06" || getHoraActual() == "23:06"){
                observer.notificar("Es la hora")
            }
        }
    }

    fun getHoraActual(): String{
        val fechaActual = LocalDateTime.now()
        val formateadorHora = DateTimeFormatter.ofPattern("HH:mm")
        val horaFormateada = fechaActual.format(formateadorHora)

        return horaFormateada
    }

}