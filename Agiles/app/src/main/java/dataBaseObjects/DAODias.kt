package dataBaseObjects

import objetos.Dia
import objetos.Observer

object DAODias {

    var observadores = ArrayList<Observer>()
    var listaDias = ArrayList<Dia>()

    private fun crearDiasScript(){

        listaDias.clear()

        var dia1 = Dia ("Lunes","7:00","8:30")
        var dia2 = Dia ("Martes","8:00","9:30")
        var dia3 = Dia ("Miercoles","9:00","10:30")
        var dia4 = Dia ("Jueves","10:00","11:30")
        var dia5 = Dia ("Viernes","3:00","4:30")

        listaDias.add(dia1)
        listaDias.add(dia2)
        listaDias.add(dia3)
        listaDias.add(dia4)
        listaDias.add(dia5)

    }

    fun getDias():ArrayList<Dia>{
        //TODO: Conectarse a la base de datos y regresar la lista
        crearDiasScript()
        return listaDias
    }

    fun getDia(index:Int): Dia {
        crearDiasScript()

        return listaDias.get(index)
    }

    fun limpiar(){
        listaDias.clear()
    }

    fun notificar(){
        for(i in observadores){
            i.notificar("Dias")
        }
    }
}