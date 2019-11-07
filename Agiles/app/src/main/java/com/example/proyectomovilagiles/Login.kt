package com.example.proyectomovilagiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dataBaseObjects.DAOMaestro
import kotlinx.android.synthetic.main.activity_login.*
import objetos.Maestro

class Login : AppCompatActivity() {
    //Booleano que muestra donde buscara los datos la app,
    // si es falso es para alumnos, si es verdadero es para profesores.
    var tipo: Boolean = false
    var maestro = Maestro()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        DAOMaestro.crearMaestrosScript()

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
                btnTipo.text = "Soy Profesor"
                tipo = false
                println(tipo)
            }else{
                btnTipo.text = "Soy Alumno"
                tipo = true
                println(tipo)
            }
        }

        btnLogin.setOnClickListener {
            var id = txtUsuario.text.toString()
            var pass = txtPass.text.toString()
            if(validacion(id,pass)){
                if(tipo){
                    val intent = Intent(this, MenuMateriasProfesor::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, MenuMateriasAlumno::class.java)
                    startActivity(intent)
                }
                preferencias.setId(id)
                preferencias.setPass(pass)

            }else{
               incorrecto.text = "*Credenciales invalidas*"
            }

        }
    }

    fun validacion(id:String, pass:String):Boolean{

        var maistro = DAOMaestro.getMaestro(id)
        if(maistro.contrasena.equals(pass)){
            maestro = maistro
            return true
        }else{
            return false
        }
    }
}
