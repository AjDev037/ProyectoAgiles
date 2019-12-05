package com.example.proyectomovilagiles.legacy

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectomovilagiles.R
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_asistencia_alumno.*
import objetos.Asistencia
import objetos.Materia

class AsistenciaAlumnoLegacy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asistencia_alumno)


        //intent.putExtra("idClase", cla.id)
        //intent.putExtra("alumno",id.nombre)
        //intent.putExtra("materia", mate)

        var asistencia = intent.getSerializableExtra("asist") as Asistencia
        var hito = intent.getStringExtra("hito")
        var nom = intent.getStringExtra("alumno")
        var id = intent.getStringExtra("idClase")
        var idAl = intent.getStringExtra("idAL")
        println("LA ID DEL ALUMNO ES: $idAl")
        var materia = intent.getSerializableExtra("materia") as Materia
        var revisado = intent.getBooleanExtra("revisado",false)

        if(!revisado){
            for(c in materia.clases){
                if(c.id == id){
                    println("ENTRE CON LA COSA ESA")
                    println(idAl)
                    c.revisados.add(idAl!!)
                    DAOMaterias.agregarMaterias(materia)
                    break
                }
            }
        }



        //DAOAsistencias.registrarAsistencia(materia,clase,asistencia)
        //DAOMaterias.agregarMaterias(materia)



        if(asistencia.hora != ""){
            nomAlumno.text = nom
            if(asistencia.estado == 0){
                txtAsistencia.text = "Retardo"
            }else if(asistencia.estado == 1){
                txtAsistencia.text = "Asistencia"
            }else{
                txtAsistencia.text = "Falta"
            }
            txtHora.text = asistencia.hora
        }else{
            nomAlumno.text = ""
            txtAsistencia.text = "No registrada"
            txtHora.text = ""
        }

        txtHito.text = hito


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
