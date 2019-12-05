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
fun getHoraActual(): String {
    val fechaActual = LocalDateTime.now()
    val formateadorHora = DateTimeFormatter.ofPattern("HH:mm")
    val horaFormateada = fechaActual.format(formateadorHora)

    return horaFormateada
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFechaActual(): String {
    val fechaActual = LocalDateTime.now()
    val formateadorFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val fechaFormateada = fechaActual.format(formateadorFecha)

    return fechaFormateada
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDiaActual(): String {
    val fechaActual = LocalDateTime.now()
    val diaActual = fechaActual.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale("es", "ES"))
    val diaFormateado = diaActual.toString().capitalize()

    return diaFormateado
}

fun getDiaActualAsDOW(): DayOfWeek {
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

fun getIDFechaClase(horario: Horario, dia: DayOfWeek): String? {
    //Obtenemos la fecha actual y la formateamos a ddMMyy o ([dia][dia][mes][mes][anio][anio])
    val fechaActual = LocalDateTime.now()
    val formateadorFecha = DateTimeFormatter.ofPattern("ddMMyy")
    val fechaFormateada = fechaActual.format(formateadorFecha)

    //La hora temporal la asignamos como un string vacio para la comprobacion mas adelante
    var horaTemp: String = ""

    //Recorremos los dias en el horario
    for (i in horario.dias) {
        //Si un dia (i) del horario (horario) es igual al dia (DayOfWeek)
        if (getDiaSemanaAsDOW(i) == dia) {
            //Asignamos la hora temporal a la hora de inicio (ini) de la clase (i)
            horaTemp = i.ini
            //Terminamos el ciclo
            break
        }

    }

    //Si hora temp sigue estando vacio
    if (horaTemp == "") {
        //Si no se encontro la hora del dia actual, se regresa un nulo
        return null
    }

    //Quitamos los dos puntos de la hora actual en la hora temporal
    horaTemp = horaTemp.replace(":", "")

    //Regresamos el codigo generado al sumar la fecha formateada y la hora temporal formateada
    return fechaFormateada + horaTemp
}

fun getDiaFromHorario(horario: Horario, dia: DayOfWeek): Dia? {
    //Recorremos los dias en el horario
    for (i in horario.dias) {
        //Si un dia (i) del horario (horario) es igual al dia (DayOfWeek)
        if (getDiaSemanaAsDOW(i) == dia) {
            //Regresamos el dia (i)
            return i
        }
    }

    //Regresa un nulo si el dia actual no se encuentra en el horario
    return null
}

fun getFechaValueFromFecha(fecha: String): Int {
    //Separamos la fecha
    val fechaSplitted = fecha.split("/")

    //Si el tamano de la fecha separada es de 3
    if (fechaSplitted.size == 3) {
        //Regresamos un entero conformado por el anio, luego el mes y al final el dia
        return (fechaSplitted[2] + fechaSplitted[1] + fechaSplitted[0]).toInt()

    } else {
        //Si el tamano esta mal, regresamos un 0
        return 0
    }
}