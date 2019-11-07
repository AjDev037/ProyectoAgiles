package dataBaseObjects

import android.os.Build
import androidx.annotation.RequiresApi
import objetos.Asistencia
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DAOAsistencias {

    var listaAsistencia = ArrayList<Asistencia>()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun crearAsistenciasScript(){

        listaAsistencia.clear()
        val fechaActual = LocalDateTime.now()
        val formateadorHora = DateTimeFormatter.ofPattern("HH:mm")
        val horaFormateada = fechaActual.format(formateadorHora)

        var asistencia1 = Asistencia (DAOAlumnos.getAlumno("00000111111"),1,horaFormateada)
        var asistencia2 = Asistencia (DAOAlumnos.getAlumno("00000222222"),0,horaFormateada)
        var asistencia3 = Asistencia (DAOAlumnos.getAlumno("00000333333"),1,horaFormateada)
        var asistencia4 = Asistencia (DAOAlumnos.getAlumno("00000444444"),-1,horaFormateada)
        var asistencia5 = Asistencia (DAOAlumnos.getAlumno("00000555555"),1,horaFormateada)

        listaAsistencia.add(asistencia1)
        listaAsistencia.add(asistencia2)
        listaAsistencia.add(asistencia3)
        listaAsistencia.add(asistencia4)
        listaAsistencia.add(asistencia5)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAsistencias():ArrayList<Asistencia>{
        //TODO: Conectarse a la base de datos y regresar la lista
        crearAsistenciasScript()
        return listaAsistencia
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAsistencia(id:String): Asistencia {
        crearAsistenciasScript()
        return listaAsistencia.get(listaAsistencia.indexOf(Asistencia(DAOAlumnos.getAlumno(id))))
    }
}