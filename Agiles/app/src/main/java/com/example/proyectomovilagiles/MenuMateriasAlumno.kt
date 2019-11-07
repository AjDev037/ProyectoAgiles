package com.example.proyectomovilagiles

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.RequiresApi
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_menu_materias.*
import kotlinx.android.synthetic.main.materia_cardview.view.*
import objetos.Materia

class MenuMateriasAlumno : AppCompatActivity() {

    var siguienteMateria:Materia? = null
    var listaMaterias = ArrayList<Materia>()
    var mats = ArrayList<Materia>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_materias)

        val preferencias = MyPreference(this)

        var id = intent.getStringExtra("id")

        mats = DAOMaterias.getMateriasAlumno(id)
        
        var adaptador = AdaptadorMateria(this, mats)
        listview.adapter = adaptador

        btnSalir.setOnClickListener {
            preferencias.setPass("")
            preferencias.setId("")
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun siguienteMateria(): Materia{

        var actual:Materia? = null

        for (i in 0..mats.size){
            for (j in 0..mats[i].horario!!.dias.size){

                if(mats[i].horario!!.dias[i].diaSemana == getDiaActual()){

                    if(mats[i].horario!!.dias[i].ini >= getHoraActual())

                        if(actual == null){
                            actual = mats[i]
                        } else if (actual.horario!!.dias[i].ini > mats[i].horario!!.dias[i].ini) {
                            actual = mats[i]
                        }
                }
            }
        }

        return actual!!
    }

    fun crearMaterias(){
        var materia1 = Materia("Materia1", "Hoy", "Hora Actual", "1800s", R.drawable.ic_backgroundtest)
        var materia2 = Materia("Materia2", "Manana", "Hora Actual", "1800s", R.drawable.ic_backgroundtest)

        listaMaterias.add(materia1)
        listaMaterias.add(materia2)
    }

    private class AdaptadorMateria:BaseAdapter{

        var contexto: Context? = null
        var materias = ArrayList<Materia>()

        constructor(context: Context, materias: ArrayList<Materia>){
            contexto = context
            this.materias = materias
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var inflador = LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.materia_cardview, null)
            var materia = materias[position]


            //TODO("Agregar una imagen de fondo para la carta, separada de la materia")
           // vista.card.setBackgroundResource(materia.imagen!!)
           // vista.materia_foto.setImageResource(materia.imagen!!)
            vista.materia_nombre.text = materia.nombre
            vista.materia_fecha.text = "miaw"
            vista.materia_hora.text = "miaw"
            vista.materia_salon.text = materia.salon

            vista.setOnClickListener{
                val intent = Intent(contexto, MateriaCalendario::class.java)

                //TODO("Add extras for next activity")

                contexto?.startActivity(intent)

            }

            return vista
        }

        override fun getItem(position: Int): Any {
            return materias[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return materias.size
        }


    }
}
