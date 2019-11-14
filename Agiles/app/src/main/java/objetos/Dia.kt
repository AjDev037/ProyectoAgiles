package objetos

import java.io.Serializable
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import java.util.*

class Dia (var diaSemana:String, var ini:String, var fin:String): Serializable {
    constructor():this("","","")
}
