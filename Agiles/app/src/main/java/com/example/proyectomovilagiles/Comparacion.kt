package com.example.proyectomovilagiles

import objetos.Observer


class Comparacion(var obs: Observer, var db: DbHandler) : Runnable {

    //Revisa si ya se envio la notificacion una vez, ahorita no sirve de nada
    //pero eso deberia hacer.
    var notificado = false

    var tempTimeToNotify: Long = 60000

    override fun run() {
        while (true) {
            //Se mete a la bd para leer las materias del usuario.
            for (mat in db.leerDatos()) {
                //Compara si el dia de hoy es el dia de alguna de las materias.
                if (mat.dia == getDiaActual()) {
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
                    val horaActualInt =
                        (horaActualSplitted[0].toInt() * 60) + (horaActualSplitted[1].toInt())

                    // NOTA: AQUI ES DONDE LA COMPARACION deberia ser de de 15 minutos
                    // DE DIFERENCIA EN LUGAR DE LA MERA HORA>
                    if (horaMateriaInt - horaActualInt in 14..15
                        && !notificado
                    ) {
                        //Si cumple la condicion manda a notificar al observador para que
                        // envie el mensaje al telefono.
                        obs.notificar(mat.nombre)
                        notificado = true
                    } else {
                        notificado = false
                    }
                }
            }
            //Hago dormir el thread un ratito para que no este siempre en segundo plano
            //haciendo el run.
            Thread.sleep(tempTimeToNotify)
        }
    }
}