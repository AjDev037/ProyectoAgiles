package com.example.proyectomovilagiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    //Booleano que muestra donde buscara los datos la app,
    // si es falso es para alumnos, si es verdadero es para profesores.
    var tipo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val preferencias = MyPreference(this)

        val intent = Intent(this, MenuMateriasProfesor::class.java)

        if(preferencias.getId() != "" && preferencias.getPass() != ""){

            var id = preferencias.getId()
            var contra = preferencias.getPass()
            if(validacion(id!!,contra!!)){
                startActivity(intent)
            }
        }

        btnTipo.setOnClickListener{
            if(tipo){
                btnTipo.text = "Soy Alumno"
            }else{
                btnTipo.text = "Soy Profesor"
            }
        }

        btnLogin.setOnClickListener {
            var id = txtUsuario.text.toString()
            var pass = txtPass.text.toString()
            if(validacion(id,pass)){
                preferencias.setId(id)
                preferencias.setPass(pass)
                startActivity(intent)
            }else{
               incorrecto.text = "*Credenciales invalidas*"
            }

        }
    }

    fun validacion(id:String, pass:String):Boolean{

        if(id.equals("1") && pass.equals("1")){

            return true
        }

        return false
    }
}
