package objetos

import java.io.Serializable

class Materia(var nombre:String,var salon:String, var imagen:Int?,var alumnos: ArrayList<Alumno>?, var maestro: Maestro?, var imgFondo:Int? , var horario:Horario?):Serializable{
 constructor() :this ("" ,"", null,null, null,null,null)

 //ONLY FOR DEVELOPMENT PURPOSES
 constructor(s: String, s1: String, s2: String, s3: String, imagen: Int) : this(s , s1, imagen,null,null,imagen,null)
}