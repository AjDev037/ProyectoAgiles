package com.example.proyectomovilagiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dataBaseObjects.DAOAlumnos
import dataBaseObjects.DAOMaestro
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_login.*
import objetos.Alumno
import objetos.Maestro

class Login : AppCompatActivity() {
    //Booleano que muestra donde buscara los datos la app,
    // si es falso es para alumnos, si es verdadero es para profesores.
    var tipo: Boolean = false
    var maestro = Maestro()
    var alumno = Alumno()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        DAOMaterias.limpiar()
        DAOMaestro.crearMaestrosScript()
        DAOAlumnos.crearAlumnosScript()
        DAOMaterias.crearMaestrosScript()

        val preferencias = MyPreference(this)

        val intent = Intent(this, MenuMateriasProfesor::class.java)

        /*if(preferencias.getId() != "" && preferencias.getPass() != ""){

            var id = preferencias.getId()
            var contra = preferencias.getPass()
            if(validacion(id!!,contra!!)){
                if(tipo){
                    val intent = Intent(this, MenuMateriasProfesor::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, MenuMateriasAlumno::class.java)
                    startActivity(intent)
                }
            }
        }*/

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
                    intent.putExtra("id",id)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, MenuMateriasAlumno::class.java)
                    intent.putExtra("id",id)
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
        if(tipo){
            var maistro = DAOMaestro.getMaestro(id)
            if(maistro.contrasena.equals(pass)){
                maestro = maistro
                return true
            }else{
                return false
            }
        }else{
            var alu = DAOAlumnos.getAlumno(id)
            if(alu.contrasena.equals(pass)){
                alumno = alu
                return true
            }else{

            }
            return false
        }

    }
}
