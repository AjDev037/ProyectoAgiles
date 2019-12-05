package objetos

import java.io.Serializable

class Clase( var id:String ,var dia: Dia, var fecha:String, var asistencias: ArrayList<Asistencia>, var salon:String, var hito:String, var revisados:ArrayList<String>) : Serializable {
    constructor():this("",Dia(),"",ArrayList<Asistencia>(),"", "",ArrayList<String>())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Clase

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}
