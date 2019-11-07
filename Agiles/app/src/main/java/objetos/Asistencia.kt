package objetos

import java.io.Serializable

class Asistencia(var alumno: Alumno, var estado: Int, var hora:String) : Serializable {
    /*Estado: 1 = Asistencia
              0 = Retardo
             -1 = Falta
    */
    constructor(alumno: Alumno) : this(alumno, 0,"")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Asistencia

        if (alumno != other.alumno) return false

        return true
    }

    override fun hashCode(): Int {
        return alumno.hashCode()
    }


}