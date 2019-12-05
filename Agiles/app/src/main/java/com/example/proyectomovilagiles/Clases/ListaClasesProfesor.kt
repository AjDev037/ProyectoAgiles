package com.example.proyectomovilagiles.Clases

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.proyectomovilagiles.*
import com.example.proyectomovilagiles.Asistencia.ListaAsistenciaProfesor
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_lista_clases_profesor.*
import kotlinx.android.synthetic.main.llenar_clases.view.*
import objetos.*

class ListaClasesProfesor : AppCompatActivity(), Observer {

    var clases = ArrayList<Clase>()
    var horario = Horario()
    var idMat = ""
    var materia = Materia()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clases_profesor)

        idMat = intent.getStringExtra("idMat")!!
        DAOMaterias.observadores.add(this)
        DAOMaterias.crearMateriasScript()

        //Esperaremos a que el DAO nos notifique
    }
    private class AdaptadorClientes : BaseAdapter {

        var context: Context
        var clases: ArrayList<Clase>? = null
        var mat = Materia()
        var horario = Horario()

        constructor(context: Context, clases: ArrayList<Clase>, materia:Materia, horario:Horario) {
            this.context = context
            this.clases = clases
            this.horario = horario
            this.mat = materia
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var layout = LayoutInflater.from(context)
            var vista = layout?.inflate(R.layout.llenar_clases, null)!!
            var cla = clases!![position]

            vista.diaClase.text = cla.dia.diaSemana
            vista.horas.text = cla.dia.ini
            vista.salonClase.text = cla.salon
            vista.txtFecha.text = cla.fecha

            vista.setOnClickListener {
                val intent = Intent(context, ListaAsistenciaProfesor::class.java)
                intent.putExtra("asist",cla.asistencias)
                intent.putExtra("materia", mat)
                intent.putExtra("clase", cla)
                (context as Activity).startActivityForResult(intent,0)
            }
            return vista

        }

        override fun getItem(position: Int): Any {
            return clases?.get(position) ?: "Error"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return clases?.size ?: 0
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        DAOMaterias.limpiar()
        DAOMaterias.observadores.add(this)
        DAOMaterias.crearMateriasScript()
    }

    //Metodo de observer
    override fun notificar(name: String) {
        materia = DAOMaterias.getMateria(idMat)

        horario = materia.horario!!
        clases = materia.clases
        var salon = materia.salon

        var adaptador = AdaptadorClientes(this,clases,materia, horario)
        listasClases.adapter = adaptador

        btnNuevaClaseM.setOnClickListener {

            var idTemp = getIDFechaClase(horario, getDiaActualAsDOW())

            var clase = Clase(idTemp!!, getDiaFromHorario(horario, getDiaActualAsDOW())!!, getFechaActual(),ArrayList(),salon!!, "",ArrayList())
            materia.clases.add(clase)

            DAOMaterias.agregarMaterias(materia)

            val intent = Intent(this, ListaAsistenciaProfesor::class.java)
            intent.putExtra("asist",clase.asistencias)
            intent.putExtra("materia", materia)
            intent.putExtra("idClase", clase.id)
            this.startActivityForResult(intent,0)

            /*
            val intent = Intent(this,GenerarClase::class.java)
            intent.putExtra("horarioMat",horario)
            intent.putExtra("salon",salon)
            intent.putExtra("materia",materia)
            startActivityForResult(intent,0)

             */
        }

        //Lo removeremos al final nomas porque si uwu
        DAOMaterias.observadores.remove(this)
    }

}
