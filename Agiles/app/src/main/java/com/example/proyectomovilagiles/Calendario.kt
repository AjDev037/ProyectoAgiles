package com.example.proyectomovilagiles

import android.os.Build
import androidx.annotation.RequiresApi
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
    val diaFormateado = diaActual.toString()

    return diaFormateado
}