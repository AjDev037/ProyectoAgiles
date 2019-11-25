package dataBaseObjects

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.FirebaseDatabase
import objetos.Asistencia
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DAOAsistencias {

    var listaAsistencia = ArrayList<Asistencia>()


    fun registrarAsistencia(idMateria:String,idClase:String,asistencia:Asistencia){
        val database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("Materias").child(idMateria).child("Clases").child(idClase).child("Asistencias")
        referencia.setValue(asistencia)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAsistencia(id:String): Asistencia {
        return listaAsistencia.get(listaAsistencia.indexOf(Asistencia(DAOAlumnos.getAlumno(id))))
    }
}

/*
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAsistencias():ArrayList<Asistencia>{
        //TODO: Conectarse a la base de datos y regresar la lista
        return listaAsistencia
    }
    */