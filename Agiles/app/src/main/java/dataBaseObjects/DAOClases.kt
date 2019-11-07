package dataBaseObjects

import objetos.Clase

object DAOClases {

    var listaClases = ArrayList<Clase>()

    private fun crearClasesScript(){

        listaClases.clear()

        //IM NOT SURE THIS IS RIGHT, PLEAS SEND HELP AND PATS
        var clase1 = Clase (DAODias.getDia(1), DAOAsistencias.getAsistencias())

        listaClases.add(clase1)

    }

    fun getClases():ArrayList<Clase>{
        //TODO: Conectarse a la base de datos y regresar la lista
        crearClasesScript()
        return listaClases
    }

    fun getClase(index:Int): Clase {
        crearClasesScript()
        return listaClases.get(index)
    }
}