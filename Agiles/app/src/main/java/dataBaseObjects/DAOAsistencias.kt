package dataBaseObjects

import objetos.Asistencia

object DAOAsistencias {

    var listaAsistencia = ArrayList<Asistencia>()

    private fun crearAsistenciasScript(){

        listaAsistencia.clear()

        var asistencia1 = Asistencia (DAOAlumnos.getAlumno("00000111111"),1)
        var asistencia2 = Asistencia (DAOAlumnos.getAlumno("00000222222"),0)
        var asistencia3 = Asistencia (DAOAlumnos.getAlumno("00000333333"),1)
        var asistencia4 = Asistencia (DAOAlumnos.getAlumno("00000444444"),-1)
        var asistencia5 = Asistencia (DAOAlumnos.getAlumno("00000555555"),1)

        listaAsistencia.add(asistencia1)
        listaAsistencia.add(asistencia2)
        listaAsistencia.add(asistencia3)
        listaAsistencia.add(asistencia4)
        listaAsistencia.add(asistencia5)

    }

    fun getAsistencias():ArrayList<Asistencia>{
        //TODO: Conectarse a la base de datos y regresar la lista
        crearAsistenciasScript()
        return listaAsistencia
    }

    fun getAsistencia(id:String): Asistencia {
        crearAsistenciasScript()
        return listaAsistencia.get(listaAsistencia.indexOf(Asistencia(DAOAlumnos.getAlumno(id))))
    }
}