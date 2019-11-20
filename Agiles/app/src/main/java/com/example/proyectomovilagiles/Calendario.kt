package com.example.proyectomovilagiles

import android.os.Build
import androidx.annotation.RequiresApi
import objetos.Dia
import objetos.Horario
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun getHoraActual(): String{
    val fechaActual = LocalDateTime.now()
    val formateadorHora = DateTimeFormatter.ofPattern("HH:mm")
    val horaFormateada = fechaActual.format(formateadorHora)

    return horaFormateada
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFechaActual():String {
    val fechaActual = LocalDateTime.now()
    val formateadorFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val fechaFormateada = fechaActual.format(formateadorFecha)

    return fechaFormateada
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDiaActual():String {
    val fechaActual = LocalDateTime.now()
    val diaActual = fechaActual.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale("es","ES"))
    val diaFormateado = diaActual.toString().capitalize()

    return diaFormateado
}

fun getDiaActualAsDOW():DayOfWeek{
    val fechaActual = LocalDateTime.now()
    val diaActual = fechaActual.getDayOfWeek()

    return diaActual
}

fun getDiaSemanaAsDOW(dia: Dia): DayOfWeek {
    var diaValue: Int? = null

    when (dia.diaSemana) {
        "Lunes" -> diaValue = 1
        "Martes" -> diaValue = 2
        "Miercoles" -> diaValue = 3
        "Jueves" -> diaValue = 4
        "Viernes" -> diaValue = 5
        "Sabado" -> diaValue = 6
        "Domingo" -> diaValue = 7
    }
    return DayOfWeek.of(diaValue!!)
}

fun getIDFechaClase(horario: Horario, dia: DayOfWeek):String? {
    val fechaActual = LocalDateTime.now()
    val formateadorFecha = DateTimeFormatter.ofPattern("ddMMyy")
    val fechaFormateada = fechaActual.format(formateadorFecha)

    var horaTemp:String = ""

    for(i in horario.dias){
        if(getDiaSemanaAsDOW(i) == dia){
            horaTemp = i.ini
            break
        }

    }

    if(horaTemp == ""){
        //Si no se encontro la hora del dia actual, se regresa un nulo
        return null
    }

    horaTemp = horaTemp.replace(":","")

    return fechaFormateada + horaTemp
}

fun getDiaFromHorario(horario:Horario, dia: DayOfWeek):Dia? {
    for(i in horario.dias){
        if(getDiaSemanaAsDOW(i) == dia){
            return i
        }
    }

    //Regresa un nulo si el dia actual no se encuentra en el horario
    return null
}