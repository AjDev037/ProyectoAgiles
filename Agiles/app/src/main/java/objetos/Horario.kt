package objetos

import java.io.Serializable

class Horario(var dias: ArrayList<Dia>) : Serializable{
    constructor():this(ArrayList<Dia>())
}