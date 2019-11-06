package objetos

import java.io.Serializable

class Materia(var nombre:String,var salon:String, var imagen:Int?,var alumnos: ArrayList<Alumno>, var imgFondo:Int? , var horario:Horario?):Serializable{
 constructor() :this ("" ,"", null,ArrayList<Alumno>(),null,null)
}