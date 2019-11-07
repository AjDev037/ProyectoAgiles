package objetos

import java.io.Serializable
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import java.util.*

class Dia (var diaSemana:String, var ini:String, var fin:String): Serializable {

    fun getDiaSemanaAsDOW():DayOfWeek{

        val formatter = DateTimeFormatter.ofPattern("EEEE", Locale("es","ES"))
        val accessor = formatter.parse(diaSemana)
        return DayOfWeek.from(accessor)

    }
}