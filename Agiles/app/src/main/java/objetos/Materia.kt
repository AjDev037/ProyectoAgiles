package objetos

import java.io.Serializable

class Materia(var nombre:String, var fecha:String, var hora:String, var salon:String, var imagen:Int?):Serializable{
 constructor() :this ("" ,"","","", null)
}