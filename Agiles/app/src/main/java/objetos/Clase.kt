package objetos

import java.io.Serializable

class Clase(var dia: Dia, var asistencias: ArrayList<Asistencia>, var salon:String) : Serializable {
    constructor():this(Dia(),ArrayList<Asistencia>(),"")
}