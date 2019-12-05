package com.example.proyectomovilagiles.Clases

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.proyectomovilagiles.R
import com.example.proyectomovilagiles.getHoraActual
import com.example.proyectomovilagiles.legacy.AsistenciaAlumnoLegacy
import com.google.zxing.Result
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_lista_clases_alumno.*
import kotlinx.android.synthetic.main.llenar_clases.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import objetos.*

class ListaClasesAlumno : AppCompatActivity(), ZXingScannerView.ResultHandler, Observer {


    override fun notificar(name: String) {
        materia = DAOMaterias.getMateria(idMateria)

        var adaptador = AdaptadorClases(this,materia.clases,alumno,materia)
        listasClasesAlumno.adapter = adaptador

        btnAsistencia.setOnClickListener {
            mScanner = ZXingScannerView(this)
            setContentView(mScanner)
            mScanner?.setResultHandler(this)
            mScanner?.startCamera()
        }
        DAOMaterias.observadores.remove(this)
    }

    var clases = ArrayList<Clase>()
    var alumno = Alumno()
    var mScanner: ZXingScannerView? = null
    var materia = Materia()
    var idMateria = ""
    val retardoTiempo = 5
    var faltaTiempo = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clases_alumno)

        var mate = intent.getSerializableExtra("materia") as Materia
        idMateria = mate.id
        var idMateria = materia.id
        //clases = intent.getSerializableExtra("clases") as ArrayList<Clase>
        alumno = intent.getSerializableExtra("id") as Alumno
        //llenarClases(idMateria)
        DAOMaterias.observadores.add(this)
        DAOMaterias.crearMateriasScript()




    }



    fun llenarClases(mat: String) {
        var contexto = this
        val database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("Materias").child(mat).child("Clases")
        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    var children = p0.children


                    for (child in children) {
                        var clase = child.getValue(Clase::class.java)
                        println(clase?.asistencias?.isEmpty())
                        llenarAsistencias(clase!!, mat)
                        clases.add(clase!!)
                    }
                    var adaptador = AdaptadorClases(contexto, clases, alumno, materia)
                    listasClasesAlumno.adapter = adaptador

                }
            }
        })
    }

    fun llenarAsistencias(clase:Clase,mat:String){
        var contexto = this
        val database = FirebaseDatabase.getInstance()
        val referencia =
            database.getReference("Materias").child(mat).child("Clases").child(clase.id)
                .child("Asistencias")
        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    var children = p0.children


                    for (child in children) {
                        var asist = child.getValue(Asistencia::class.java)

                        println("ESTOY IMPRIMIENDO EL ASIST")
                        println(asist)
                        //clase.asistencias.add(asist!!)
                    }


                }
            }
        })
    }

    override fun handleResult(p0: Result?) {
        mScanner?.stopCamera()
        Log.v("HanlderResult", p0?.text)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Clase")
        builder.setMessage(p0?.text)
        val dialogo = builder.create()
        dialogo.show()
        val text = p0?.text.toString()
        val codigo = text.split(".").toTypedArray()
        println("VOY A IMPRIMIR")
        //var materia = codigo[0]
        var clase = codigo[1]
        var estado = codigo[2]
        println(materia)
        println(clase)
        println(estado)

        //Separamos la hora del QR que viene en el estado
        val estadoSplitted = estado.split(":")
        //Multiplicamos las horas por 60 para obtener los minutos y los agregamos a los minutos de la hora
        val horaEstadoInt = (estadoSplitted.get(0).toInt() * 60) + (estadoSplitted.get(1).toInt())


        val horaActual = getHoraActual()
        //Separamos la hora actual
        val horaActualSplitted = horaActual.split(":")
        //Multiplicamos las horas por 60 para obtener los minutos y los agregamos a los minutos de la hora
        val horaActualInt =
            (horaActualSplitted.get(0).toInt() * 60) + (horaActualSplitted.get(1).toInt())

        var estadoAsistencia: Int? = null

        //Si la hora actual de registro menos la hora del QR, es menor al tiempo definido para el retardo
        if ((horaActualInt - horaEstadoInt) <= retardoTiempo) {
            //Lo registramos como asistencia
            estadoAsistencia = 1
        } else if ((horaActualInt - horaEstadoInt) <= faltaTiempo) {
            //En caso de que la hora actual de registro sea menor al tiempo definido para la falta
            //Lo tomamos como retardo
            estadoAsistencia = 0
        } else {
            //En caso de que la hora si fuese mayor al definido para la falta
            //Se tomara como falta
            estadoAsistencia = -1
        }

        //Creamos la asistencia
        var asistencia = Asistencia(alumno, estadoAsistencia!!, estadoAsistencia!!, getHoraActual())
        for (c in materia.clases) {
            if (c.id == clase) {
                c.asistencias.add(asistencia)
            }
        }

        //DAOAsistencias.registrarAsistencia(materia,clase,asistencia)
        DAOMaterias.agregarMaterias(materia)
        dialogo.dismiss()
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        mScanner?.stopCamera()
    }

    private class AdaptadorClases : BaseAdapter {

        var context: Context
        var clases: ArrayList<Clase>? = null
        var id  = Alumno()
        var mate = Materia()

        constructor(context: Context, clases: ArrayList<Clase>, nom:Alumno,mat:Materia) {
            this.context = context
            this.clases = clases
            id = nom
            mate = mat
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var layout = LayoutInflater.from(context)
            var vista = layout?.inflate(R.layout.llenar_clases, null)!!
            var cla = clases!![position]
            var revisado = false
            if (vista != null) {
                vista.diaClase.text = cla.dia.diaSemana
                vista.horas.text = cla.dia.ini
                vista.salonClase.text = cla.salon
                vista.txtFecha.text = cla.fecha


                for( r in cla.revisados){
                    if(r == id.id){
                        revisado = true
                        break
                    }
                }

                if(!revisado){
                   vista.diaClase.setTextColor(Color.GREEN)
                    vista.horas.setTextColor(Color.GREEN)
                    vista.salonClase.setTextColor(Color.GREEN)
                    vista.txtFecha.setTextColor(Color.GREEN)
                }


            }

            vista.setOnClickListener {
                val intent = Intent(context, AsistenciaAlumnoLegacy::class.java)
                var auxiliar = Asistencia()
                //println("DATOS DE LA ASISTENCIA")
                println(cla.asistencias)


                for(x in cla.asistencias){
                    if(x.alumno.id == id.id){
                        auxiliar = x
                        break
                    }
                }

                intent.putExtra("asist",auxiliar)
                intent.putExtra("hito", cla.hito)
                intent.putExtra("idClase", cla.id)
                intent.putExtra("alumno",id.nombre)
                intent.putExtra("idAL",id.id)
                intent.putExtra("materia", mate)
                intent.putExtra("revisado",revisado)
                println("EL ESTADO DE REVISION ES $revisado")

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
        print("Volvi")

        DAOMaterias.limpiar()
        DAOMaterias.observadores.add(this)
        DAOMaterias.crearMateriasScript()


    }
}
