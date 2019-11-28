package objetos

import java.io.Serializable

class Clase( var id:String ,var dia: Dia, var fecha:String, var asistencias: ArrayList<Asistencia>, var salon:String, var hito:String) : Serializable {
    constructor():this("",Dia(),"",ArrayList<Asistencia>(),"", "")
}
