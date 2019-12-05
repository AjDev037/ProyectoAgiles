package com.example.proyectomovilagiles.Asistencia

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.proyectomovilagiles.R
import com.example.proyectomovilagiles.getFechaActual
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_lista_asistencia_profesor.*
import kotlinx.android.synthetic.main.llenar_asistencia_profesor.view.*
import objetos.Asistencia
import objetos.Clase
import objetos.Horario
import objetos.Materia

class ListaAsistenciaProfesor : AppCompatActivity() {

    var asistencias = ArrayList<Asistencia>()
    val hitoLength = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_asistencia_profesor)

        asistencias = intent.getSerializableExtra("asist") as ArrayList<Asistencia>

        var materia = intent.getSerializableExtra("materia") as Materia
        val horario = materia.horario!!
        val idMat = materia.id

        var clase = intent.getStringExtra("idClase")
        var adaptador =
            AdaptadorAsistencias(
                this,
                asistencias, materia, clase
            )
        listasAsistencias.adapter = adaptador

        btnTomarAsist.setOnClickListener {
            generarQR(horario, materia)
        }

        btnActualizar.setOnClickListener {
            var claseTemp: Clase? = null

            for (i in materia.clases) {
                if (i.id == clase) {
                    claseTemp = i
                    break
                }
            }

            if (claseTemp != null) {
                actualizarClase(materia, claseTemp)
            }
        }
    }

    fun actualizarClase(materia: Materia, clase: Clase) {
        //Si la fecha de la clase es despues o igual a hoy
        if (clase.fecha >= getFechaActual()) {
            //Agregamos un hito
            if(txtHito.text.toString().length > hitoLength){
                Toast.makeText(this,
                    "El Hito no puede ser de una longitud mayor que $hitoLength", Toast.LENGTH_LONG).show()
            } else {
                clase.hito = txtHito.text.toString().substring(0, hitoLength)

                //Actualizamos los datos de la clase ?
                DAOMaterias.agregarMaterias(materia)
            }
        }
    }


    fun generarQR(horario: Horario, materia: Materia) {
        val intent = Intent(this, TomarAsistencia::class.java)
        intent.putExtra("horarioMat", horario)
        intent.putExtra("materia", materia)
        startActivityForResult(intent, 0)
    }

    private class AdaptadorAsistencias : BaseAdapter {

        var context: Context
        var asistencias: ArrayList<Asistencia>? = null
        var clase = ""
        var mat = Materia()

        constructor(
            context: Context,
            asistencias: ArrayList<Asistencia>,
            materia: Materia,
            cla: String
        ) {
            this.context = context
            this.asistencias = asistencias
            mat = materia
            clase = cla
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var layout = LayoutInflater.from(context)
            var vista = layout?.inflate(R.layout.llenar_asistencia_profesor, null)!!
            var asi = asistencias!![position]

            if (vista != null) {
                vista.nomAlumno.text = asi.alumno.nombre
                if (asi.estado == 0) {
                    vista.txtAsistencia.text = "Retardo"
                } else if (asi.estado == 1) {
                    vista.txtAsistencia.text = "Asistencia"
                } else {
                    vista.txtAsistencia.text = "Falta"
                }
                vista.txtHora.text = asi.hora

            }

            vista.setOnClickListener {

                val mAlertDialog = AlertDialog.Builder(context)
                //Le damos un icono
                mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
                //Le damos un titulo
                mAlertDialog.setTitle("Justificacion")
                //Y le ponemos un mensaje
                mAlertDialog.setMessage("Desea justificar esta asistencia?")

                //Le damos un listener a la accion de continuar y un texto
                mAlertDialog.setPositiveButton("Si") { dialog, id ->
                    //Le sumamos uno al estado
                    asi.estado += 1
                    this.notifyDataSetChanged()
                    var posicion: Int = 0
                    var posicionC = 0
                    //Aqui puedes poner el llamado a la base de datos uwu
                    for (c in mat.clases) {
                        if (c.id == clase) {
                            for (a in c.asistencias!!) {
                                if (a.alumno.id == asi.alumno.id) {
                                    posicion = c.asistencias.indexOf(a)
                                    posicionC = mat.clases.indexOf(c)
                                }
                            }
                        }
                    }
                    mat.clases[posicionC].asistencias[posicion] = asi
                    DAOMaterias.agregarMaterias(mat)
                    //DAOMaterias.crearMateriasScript()
                }

                //Le damos un listener a la accion negativa y un texto
                mAlertDialog.setNegativeButton("No") { dialog, id ->
                    //Si dijo que no, no hacemos nada...

                }

                //Si el estado original no es igual al estado, es porque ya lo actualizamos
                if (asi.estadoOriginal != asi.estado) {
                    //Mostramos un texto
                    Toast.makeText(
                        context,
                        "No se puede actualizar dos veces una asistencia",
                        Toast.LENGTH_LONG
                    ).show()
                }
                //Si el estado es menor a 1, entonces se trata o de una falta o de un retardo
                else if (asi.estado < 1) {
                    //Mostramos el cuadro de dialogo
                    mAlertDialog.show()
                    //Si no, se trata de una asistencia, por lo que no hay nada que justificar
                } else {
                    //Mostramos un texto
                    Toast.makeText(context, "No puede justificar una asistencia", Toast.LENGTH_LONG)
                        .show()
                }

                //Para fines de prueba mostramos el valor del estado
                Toast.makeText(context, asi.estado.toString(), Toast.LENGTH_LONG).show()


                for (a in asistencias!!) {
                    println(a.estado)
                }
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

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
