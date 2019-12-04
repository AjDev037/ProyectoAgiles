package com.example.proyectomovilagiles.Materias

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
import com.example.proyectomovilagiles.*
import com.example.proyectomovilagiles.Clases.ListaClasesAlumno
import com.example.proyectomovilagiles.Login.Login
import com.example.proyectomovilagiles.Preferencias.MyPreference
import dataBaseObjects.DAOAlumnos
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_menu_materias.*
import kotlinx.android.synthetic.main.materia_cardview.view.*
import objetos.Alumno
import objetos.Dia
import objetos.Horario
import objetos.Materia
import androidx.core.content.ContextCompat.startForegroundService
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast


class MenuMateriasAlumno : AppCompatActivity() {

    var siguiente:Materia? = null
    var listaMaterias = ArrayList<Materia>()
    var mats = ArrayList<Materia>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_materias)
        DAOAlumnos.crearAlumnosScript()
        val preferencias = MyPreference(this)
        if(preferencias.getVacio()){
            Toast.makeText(this,"BD VACIA", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"BD Con datos", Toast.LENGTH_LONG).show()
        }

        val alumno = intent.getSerializableExtra("alumno") as Alumno


        mats = DAOMaterias.getMateriasAlumno(alumno.id)
        iniciarServicio()

        siguiente = getSiguienteCardMateria()
        if (siguiente != null) {
            mats.add(0, siguiente!!)
        }


        var adaptador = AdaptadorMateria(this, mats,alumno)
        listview.adapter = adaptador

        btnSalir.setOnClickListener {
            preferencias.setPass("")
            preferencias.setId("")
            preferencias.setVacio(true)
            val db = DbHandler(this)
            db.borrarDatos()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getSiguienteCardMateria(): Materia? {

        var actual:Materia? = null

        //Recorremos las materias
        for (i in 0 until mats.size) {
            //Y recorremos los dias de la materia
            for (j in 0 until mats[i].horario!!.dias.size) {
                //Si el dia de la semana es igual al actual\
                if(mats[i].horario!!.dias[j].diaSemana == getDiaActual()){
                    //Y la hora del dia es mayor que la actual
                    if(mats[i].horario!!.dias[j].ini >= getHoraActual())
                        //Y si actual es nulo
                        if(actual == null){
                            //Guardamos esa materia en actual
                            actual = mats[i]
                        //Si no, comparamos la hora de este dia y materia con el que ya teniamos guardado
                        //para ver si esta clase iria antes
                        } else if (actual.horario!!.dias[j].ini > mats[i].horario!!.dias[j].ini) {
                            //Si es asi, cambiamos actual a esta otra materia
                            actual = mats[i]
                        }
                }
            }
        }

        //Regresaremos o un nulo o la siguiente materia proxima del dia
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

    fun iniciarServicio(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, MyService::class.java))
        } else {
            startService(Intent(this, MyService::class.java))
        }
    }

    fun crearMaterias(){
        var materia1 = Materia("Materia1", "Hoy", "Hora Actual", "1800s",
            R.drawable.ic_backgroundtest
        )
        var materia2 = Materia("Materia2", "Manana", "Hora Actual", "1800s",
            R.drawable.ic_backgroundtest
        )

        listaMaterias.add(materia1)
        listaMaterias.add(materia2)
    }

    private class AdaptadorMateria:BaseAdapter{

        var contexto: Context? = null
        var materias = ArrayList<Materia>()
        var id = Alumno()

        constructor(context: Context, materias: ArrayList<Materia>,id:Alumno){
            contexto = context
            this.materias = materias
            this.id = id
            llenarBD()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var inflador = LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.materia_cardview, null)

            var materia = materias[position]


            //TODO("Agregar una imagen de fondo para la carta, separada de la materia")
            vista.card.setBackgroundResource(R.drawable.ic_class_black_24dp)
            println(R.drawable.ic_class_black_24dp)
           // vista.materia_foto.setImageResource(materia.imagen!!)
            vista.materia_nombre.text = materia.nombre

            //Guardamos la siguiente fecha para su uso posterior
            var sf = siguienteFecha(materia.horario!!)

            vista.materia_fecha.text = sf.diaSemana
            vista.materia_hora.text = sf.ini
            vista.materia_salon.text = materia.salon

            vista.setOnClickListener{
                val intent = Intent(contexto, ListaClasesAlumno::class.java)

                //TODO("Add extras for next activity")
                intent.putExtra("materia",materia)
                intent.putExtra("id",id)
                contexto?.startActivity(intent)

            }

            return vista
        }

        fun siguienteFecha(horario: Horario): Dia {

            var diaSiguiente:Dia? = null

            //Recorremos los dias en el horario
            for (i in horario.dias){
                //Y los comparamos con hoy para ver si estan despues
                if(getDiaSemanaAsDOW(i) >= getDiaActualAsDOW()){
                    //De ser asi, el diaSiguiente sera ese dia que comparamos
                    diaSiguiente = i
                    break
                }
            }

            //Si no obtuvimos ningun dia que sea despues de hoy
            if(diaSiguiente == null){
                //Decimos que el siguiente dia sera el primero del horario de la materia
                //Digase hoy es viernes y la siguiente clase seria el Lunes
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

        fun llenarBD(){
            var preferencias = MyPreference(contexto!!)
            if(preferencias.getVacio()){
                val db = DbHandler(contexto!!)
                for( m in materias){
                    for( h in m.horario!!.dias){
                        db.insertarDatos(h.diaSemana,h.ini,m.nombre)
                    }
                }
                preferencias.setVacio(false)
            }
        }


    }
}
