package com.example.proyectomovilagiles.legacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectomovilagiles.R
import kotlinx.android.synthetic.main.activity_asistencia_alumno.*
import objetos.Asistencia

class AsistenciaAlumno : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asistencia_alumno)


        var asistencia = intent.getSerializableExtra("asist") as Asistencia

        nomAlumno.text = asistencia.alumno.nombre
        if(asistencia.estado == 0){
            txtAsistencia.text = "Retardo"
        }else if(asistencia.estado == 1){
            txtAsistencia.text = "Asistencia"
        }else{
            txtAsistencia.text = "Falta"
        }
        txtHora.text = asistencia.hora

    }
}
