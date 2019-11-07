package objetos

import java.io.Serializable
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import java.util.*

class Dia(var diaSemana: String, var ini: String, var fin: String) : Serializable {

    fun getDiaSemanaAsDOW(): DayOfWeek {

        var diaValue: Int? = null

        when (diaSemana) {
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
}