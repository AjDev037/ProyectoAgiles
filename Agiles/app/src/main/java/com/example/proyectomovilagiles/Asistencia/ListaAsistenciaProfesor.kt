package com.example.proyectomovilagiles.Asistencia

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.proyectomovilagiles.R
import com.example.proyectomovilagiles.getFechaActual
import com.example.proyectomovilagiles.getFechaValueFromFecha
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_lista_asistencia_profesor.*
import kotlinx.android.synthetic.main.llenar_asistencia_profesor.view.*
import objetos.Asistencia
import objetos.Clase
import objetos.Horario
import objetos.Materia
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList


class ListaAsistenciaProfesor : AppCompatActivity() {

    var asistencias = ArrayList<Asistencia>()
    val hitoMaxLength = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_asistencia_profesor)



        asistencias = intent.getSerializableExtra("asist") as ArrayList<Asistencia>

        for (i in asistencias){
            println(i.estado.toString() + i.alumno)
        }

        var materia = intent.getSerializableExtra("materia") as Materia
        val horario = materia.horario!!

        var clase = intent.getSerializableExtra("clase") as Clase
        var adaptador =
            AdaptadorAsistencias(
                this,
                asistencias, materia, clase.id
            )
        listasAsistencias.adapter = adaptador

        clase = materia.clases[materia.clases.indexOf(clase)]

        txtHito.text = clase.hito

        btnTomarAsist.setOnClickListener {
            generarQR(horario, materia)
        }

        btnActualizar.setOnClickListener {
            //Si la fecha de la clase es despues o igual a hoy
            if (getFechaValueFromFecha(clase.fecha) >= getFechaValueFromFecha(getFechaActual())) {
                //Permitimos que se ingrese el hito
                ingresarHito(materia, clase)
            } else {
                //Mostramos un mensaje de error
                Toast.makeText(
                    this,
                    "No se puede actualizar el hito de una clase pasada!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun ingresarHito(materia: Materia, clase: Clase) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hito")

        // Set up the input
        val input = EditText(this)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        builder.setView(input)
        // Set up the buttons
        builder.setPositiveButton(
            "Guardar"
        ) { dialog, which -> actualizarClase(materia, clase, input.text.toString()) }
        builder.setNegativeButton(
            "Cancelar"
        ) { dialog, which -> dialog.cancel() }

        builder.show()
    }


    fun actualizarClase(materia: Materia, clase: Clase, hitoText: String) {
        //Obtenemos la longitud del hito
        val hitoLenght = hitoText.length

        //Si la longitud del hito es mayor que el maximo definido
        if (hitoLenght > hitoMaxLength) {
            //Mostramos un mensaje de error
            Toast.makeText(
                this,
                "El Hito no puede ser de una longitud mayor que $hitoMaxLength",
                Toast.LENGTH_LONG
            ).show()

            //Si la longitud del hito es menor o igual que la maxima
        } else {
            //El hito de la clase lo igualamos al string
            clase.hito = hitoText
        }

        //Y actualizamos el texto
        txtHito.text = clase.hito
        //Y finalmente actualizamos los datos de la clase
        DAOMaterias.agregarMaterias(materia)
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
                    var posicion = 0
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
