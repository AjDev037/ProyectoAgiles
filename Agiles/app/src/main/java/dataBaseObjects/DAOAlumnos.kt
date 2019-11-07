package dataBaseObjects

import objetos.Alumno

object DAOAlumnos {

    var listaAlumnos = ArrayList<Alumno>()

    private fun crearAlumnosScript(){

        listaAlumnos.clear()

        var alumno1 = Alumno("Alumno1","00000111111","contrasena1")
        var alumno2 = Alumno("Alumno2","00000222222","contrasena2")
        var alumno3 = Alumno("Alumno3","00000333333","contrasena3")
        var alumno4 = Alumno("Alumno4","00000444444","contrasena4")
        var alumno5 = Alumno("Alumno5","00000555555","contrasena5")

        listaAlumnos.add(alumno1)
        listaAlumnos.add(alumno2)
        listaAlumnos.add(alumno3)
        listaAlumnos.add(alumno4)
        listaAlumnos.add(alumno5)


    }

    fun getAlumnos():ArrayList<Alumno>{
        //TODO: Conectarse a la base de datos y regresar la lista
        crearAlumnosScript()
        return listaAlumnos
    }

    fun getAlumno(id:String): Alumno {
        return listaAlumnos.get(listaAlumnos.indexOf(Alumno(id)))
    }
}