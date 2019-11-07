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
import objetos.Dia
import objetos.Horario
import objetos.Materia

class MenuMateriasAlumno : AppCompatActivity() {

    var siguiente:Materia? = null
    var listaMaterias = ArrayList<Materia>()
    var mats = ArrayList<Materia>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_materias)

        val preferencias = MyPreference(this)

        var id = intent.getStringExtra("id")

        mats = DAOMaterias.getMateriasAlumno(id)

        siguiente = getSiguienteCardMateria()
        if (siguiente != null) {
            mats.add(0, siguiente!!)
        }


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
    fun getSiguienteCardMateria(): Materia? {

        var actual:Materia? = null

        for (i in 0 until mats.size) {
            for (j in 0 until mats[i].horario!!.dias.size) {

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

        return actual
    }

    /*
    fun crearCardMateria(materia: Materia) {
        //card.setBackgroundResource(materia.imagen!!)
        //materia_foto.setImageResource(materia.imagen!!)
        materia_nombre.text = materia.nombre
        materia_fecha.text = "miaw"
        materia_hora.text = "miaw"
        materia_salon.text = materia.salon

        card.setOnClickListener {
            val intent = Intent(this, MateriaCalendario::class.java)

            //TODO("Add extras for next activity")

            startActivity(intent)
        }
    }
     */

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

            var sf = siguienteFecha(materia.horario!!)
            
            vista.materia_fecha.text = sf.diaSemana
            vista.materia_hora.text = sf.ini
            vista.materia_salon.text = materia.salon

            vista.setOnClickListener{
                val intent = Intent(contexto, MateriaCalendario::class.java)

                //TODO("Add extras for next activity")

                contexto?.startActivity(intent)

            }

            return vista
        }

        fun siguienteFecha(horario: Horario): Dia {

            var diaSiguiente:Dia? = null

            for (i in horario.dias){
                if(i.getDiaSemanaAsDOW() >= getDiaActualAsDOW()){
                    diaSiguiente = i
                }
            }

            if(diaSiguiente == null){
                diaSiguiente = horario.dias[0]
            }

            return diaSiguiente!!
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
