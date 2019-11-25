package dataBaseObjects

import objetos.Dia
import objetos.Horario
import objetos.Observer

object DAOHorarios {

    var observadores = ArrayList<Observer>()
    var listaHorarios = ArrayList<Horario>()

    private fun crearHorariosScript(){

        listaHorarios.clear()

        var listaDiasTemp = ArrayList<Dia>()

        listaDiasTemp.add(DAODias.getDia(0))
        listaDiasTemp.add(DAODias.getDia(1))
        listaDiasTemp.add(DAODias.getDia(2))

        var horario1 = Horario (listaDiasTemp)

        var listaDiasTemp2 = ArrayList<Dia>()

        listaDiasTemp2.add(DAODias.getDia(3))
        listaDiasTemp2.add(DAODias.getDia(4))

        var horario2 = Horario (listaDiasTemp2)

        listaHorarios.add(horario1)
        listaHorarios.add(horario2)

    }

    fun getHorarios():ArrayList<Horario>{
        //TODO: Conectarse a la base de datos y regresar la lista
        crearHorariosScript()
        return listaHorarios
    }

    fun getHorario(index:Int): Horario {
        crearHorariosScript()
        return listaHorarios.get(index)
    }

    fun limpiar(){
        listaHorarios.clear()
    }

    fun notificar(){
        for(i in observadores){
            i.notificar("Horarios")
        }
    }
}