package dataBaseObjects

import android.os.Build
import androidx.annotation.RequiresApi
import objetos.Clase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import objetos.Dia
import objetos.Observer

object DAOClases {

    var observadores = ArrayList<Observer>()
    var listaClases = ArrayList<Clase>()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun crearClasesScript(){

        listaClases.clear()

        //IM NOT SURE THIS IS RIGHT, PLEAS SEND HELP AND PATS
        //var clase1 = Clase (DAODias.getDia(1), "07/11/19",DAOAsistencias.getAsistencias(),"salon")
       // var clase2 = Clase(DAODias.getDia(4), "07/11/19",DAOAsistencias.getAsistencias(),"salon2")

       // listaClases.add(clase1)
       // listaClases.add(clase2)

    }

    fun crearClase(c:Clase,idMateria:String){
        val database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("Materias").child(idMateria).child("Clases").child(c.id)
        referencia.setValue(c)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getClases():ArrayList<Clase>{
        //TODO: Conectarse a la base de datos y regresar la lista
        crearClasesScript()
        return listaClases
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getClases(fecha:String):ArrayList<Clase>{
        crearClasesScript()

        var listaClasesFecha = ArrayList<Clase>()

        for(i in listaClases){
            if (i.fecha == fecha){
                listaClasesFecha.add(i)
            }
        }

        return listaClasesFecha
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getClase(index:Int): Clase {
        crearClasesScript()
        return listaClases.get(index)
    }

    fun limpiar(){
        listaClases.clear()
    }

    fun notificar(){
        for(i in observadores){
            i.notificar("Clases")
        }
    }
}