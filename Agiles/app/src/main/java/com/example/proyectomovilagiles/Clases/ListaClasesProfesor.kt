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
import com.example.proyectomovilagiles.R
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_lista_clases.*
import kotlinx.android.synthetic.main.llenar_clases.view.*
import objetos.Clase
import objetos.Horario
import objetos.Materia

class ListaClasesProfesor : AppCompatActivity() {

    var clases = ArrayList<Clase>()
    var horario = Horario()
    var idMat = ""
    var materia = Materia()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clases)

        //clases = intent.getSerializableExtra("clases") as ArrayList<Clase>

        //val idMat = intent.getSerializableExtra("materia") as Materia
        idMat = intent.getStringExtra("idMat")
        materia = DAOMaterias.getMateria(idMat)
        horario = materia.horario!!
        clases = materia.clases

        var salon = intent.getStringExtra("salon")

        var adaptador = AdaptadorClientes(this,clases,materia)
        listasClases.adapter = adaptador

        btnNuevaClaseM.setOnClickListener {


            val intent = Intent(this,GenerarClase::class.java)
            intent.putExtra("horarioMat",horario)
            intent.putExtra("salon",salon)
            intent.putExtra("materia",idMat)
            startActivityForResult(intent,0)
        }


    }
    private class AdaptadorClientes : BaseAdapter {

        var context: Context
        var clases: ArrayList<Clase>? = null
        var mat = Materia()

        constructor(context: Context, clases: ArrayList<Clase>, materia:Materia) {
            this.context = context
            this.clases = clases
            mat = materia
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var layout = LayoutInflater.from(context)
            var vista = layout?.inflate(R.layout.llenar_clases, null)!!
            var cla = clases!![position]

            if (vista != null) {
                vista.diaClase.text = cla.dia.diaSemana
                vista.horas.text = cla.dia.ini
                vista.salonClase.text = cla.salon
                vista.txtFecha.text = cla.fecha
            }

            vista.setOnClickListener {
                val intent = Intent(context, ListaAsistenciaProfesor::class.java)
                intent.putExtra("asist",cla.asistencias)
                intent.putExtra("materia", mat)
                intent.putExtra("idClase", cla.id)
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

        println("ENTRE AL ACTIVITYRESULT")

        materia = DAOMaterias.getMateria(idMat)
        horario = materia.horario!!
        clases = materia.clases


        var adaptador = AdaptadorClientes(this,clases,materia)
        listasClases.adapter = adaptador

    }

}
