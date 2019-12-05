package com.example.proyectomovilagiles

import android.os.Handler
import objetos.Observer

class ComparacionCalendarizada(var obs:Observer, var db : DbHandler) {

    var tempTimeToNotify: Long = 2000
    val calendarizador = Handler()

    fun calendarizar() {
        //Se mete a la bd para leer las materias del usuario.
        for( mat in db.leerDatos()){
            //Compara si el dia de hoy es el dia de alguna de las materias.
            if(mat.dia == getDiaActual()){
                //En caso de ser verdadero, se compara la hora para ver si es
                //la hora correcta y mandar la notificacion al usuario.

                //Obtenemos la hora actual
                val horaActual = getHoraActual()

                //Separamos la hora de la materia
                val horaSplitted = mat.hora.split(":")
                //Multiplicamos las horas por 60 para obtener los minutos y los agregamos a los minutos de la hora
                val horaMateriaInt = (horaSplitted[0].toInt() * 60) + (horaSplitted[1].toInt())

                //Separamos la hora actual
                val horaActualSplitted = horaActual.split(":")
                //Multiplicamos las horas por 60 para obtener los minutos y los agregamos a los minutos de la hora
                val horaActualInt = (horaActualSplitted[0].toInt() * 60) + (horaActualSplitted[1].toInt())

                //Validamos que la hora de la materia sea antes de que la hora actual del dispositivo
                if(horaMateriaInt > horaActualInt){
                    //Calculamos la diferencia de tiempo en milisegundos
                    tempTimeToNotify = ((horaMateriaInt - horaActualInt) * 60 * 1000).toLong()

                    //Le quitamos los 15 minutos de tiempo de notificacion
                    tempTimeToNotify -= (15 * 60 * 1000)

                    //Verificamos que el tiempo no sea menor a 0
                    if(tempTimeToNotify < 0){
                        tempTimeToNotify = 1000
                    }

                    //Calendarizamos el proceso de notificar para que notifique en el tiempo adecuado
                    calendarizador.postDelayed({notificar(mat.nombre)}, tempTimeToNotify)
                }
            }
        }
    }

    private fun notificar(nombre: String){
        obs.notificar(nombre)
    }

}