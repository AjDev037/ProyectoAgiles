package com.example.proyectomovilagiles

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_lista_clases.*
import kotlinx.android.synthetic.main.llenar_clases.view.*
import objetos.Clase

class ListaClasesProfesor : AppCompatActivity() {

    var clases = ArrayList<Clase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clases)

        clases = intent.getSerializableExtra("clases") as ArrayList<Clase>
        var adaptador = AdaptadorClientes(this,clases)
        listasClases.adapter = adaptador

    }
    private class AdaptadorClientes : BaseAdapter {

        var context: Context
        var clases: ArrayList<Clase>? = null

        constructor(context: Context, clases: ArrayList<Clase>) {
            this.context = context
            this.clases = clases
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var layout = LayoutInflater.from(context)
            var vista = layout?.inflate(R.layout.llenar_clases, null)!!
            var cla = clases!![position]

            if (vista != null) {
                vista.diaClase.text = cla.dia.diaSemana
                vista.horas.text = cla.dia.ini
                vista.salonClase.text = cla.salon
            }

            vista.setOnClickListener {
                val intent = Intent(context, ListaAsistenciaProfesor::class.java)
                intent.putExtra("asist",cla.asistencias)
                (context as Activity).startActivity(intent)
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

}