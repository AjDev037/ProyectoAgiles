package com.example.proyectomovilagiles

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_lista_asistencia_profesor.*
import kotlinx.android.synthetic.main.llenar_asistencia_profesor.view.*
import kotlinx.android.synthetic.main.llenar_clases.view.*
import objetos.Asistencia
import objetos.Clase

class ListaAsistenciaProfesor : AppCompatActivity() {

    var asistencias = ArrayList<Asistencia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_asistencia_profesor)

        asistencias = intent.getSerializableExtra("asist") as ArrayList<Asistencia>
        var adaptador = AdaptadorAsistencias(this,asistencias)
        listasAsistencias.adapter = adaptador

    }

    private class AdaptadorAsistencias : BaseAdapter {

        var context: Context
        var asistencias: ArrayList<Asistencia>? = null

        constructor(context: Context, asistencias: ArrayList<Asistencia>) {
            this.context = context
            this.asistencias = asistencias
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var layout = LayoutInflater.from(context)
            var vista = layout?.inflate(R.layout.llenar_asistencia_profesor, null)!!
            var asi = asistencias!![position]

            if (vista != null) {
                vista.nomAlumno.text = asi.alumno.nombre
                if(asi.estado == 0){
                    vista.txtAsistencia.text = "Retardo"
                }else if(asi.estado == 1){
                    vista.txtAsistencia.text = "Asistencia"
                }else{
                    vista.txtAsistencia.text = "Falta"
                }
                vista.txtHora.text = asi.hora

            }

            vista.setOnClickListener {

            }
            return vista

        }

        override fun getItem(position: Int): Any {
            return asistencias?.get(position) ?: "Error"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return asistencias?.size ?: 0
        }
    }
}
