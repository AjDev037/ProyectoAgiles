package objetos

import java.io.Serializable

class Materia(var nombre:String, var fecha:String, var hora:String, var salon:String, var imagen:Int?,var alumnos: ArrayList<Alumno>):Serializable{
 constructor() :this ("" ,"","","", null,ArrayList<Alumno>())
}