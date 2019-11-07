package dataBaseObjects

import objetos.Maestro
import objetos.Materia

object DAOMaterias {

    var listaMaterias = ArrayList<Materia>()

    private fun crearMaestrosScript(){

        listaMaterias.clear()

        var materia1 = Materia("00000001","Materia1","1824", null, DAOAlumnos.getAlumnos(), DAOMaestro.getMaestro("000001111"), null, DAOHorarios.getHorario(0),DAOClases.getClases() )
        var materia2 = Materia("00000002","Materia2","1825", null, DAOAlumnos.getAlumnos(), DAOMaestro.getMaestro("000002222"), null, DAOHorarios.getHorario(1), DAOClases.getClases() )


        listaMaterias.add(materia1)
        listaMaterias.add(materia2)


    }

    fun getMaterias():ArrayList<Materia>{
        //TODO: Conectarse a la base de datos y regresar la lista
        crearMaestrosScript()
        return listaMaterias
    }

    fun getMateria(id:String): Materia {
        crearMaestrosScript()
        return listaMaterias.get(listaMaterias.indexOf(Materia(id)))
    }
}