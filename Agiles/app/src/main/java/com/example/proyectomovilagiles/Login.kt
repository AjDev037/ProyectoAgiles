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
import java.lang.IndexOutOfBoundsException

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
        DAOMaterias.crearMateriasScript()

        val preferencias = MyPreference(this)

        //val intent = Intent(this, MenuMateriasProfesor::class.java)

        if(preferencias.getId() != "" && preferencias.getPass() != ""){

            var id = preferencias.getId()
            var contra = preferencias.getPass()
            tipo = preferencias.getTipo()!!
            println("HOOOOOOOOOOOOOLLLLLLLLLLLLLAAAAAAAAAAAAAAAAA")
            println(id)
            println(contra)
            println(tipo)
            if(validacion(id!!,contra!!,tipo)){
                if(tipo!!){
                    val intent = Intent(this, MenuMateriasProfesor::class.java)
                    intent.putExtra("id",id)
                    startActivityForResult(intent,0)
                }else{
                    val intent = Intent(this, MenuMateriasAlumno::class.java)
                    intent.putExtra("id",id)
                    startActivityForResult(intent,0)
                }
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
            if(validacion(id,pass,tipo)){
                println("ESTOY GUARDANDO LOS DATOS")
                preferencias.setId(id)
                preferencias.setPass(pass)
                preferencias.setTipo(tipo)
                println("YA LOS GUARDE")
                if(tipo){

                    val intent = Intent(this, MenuMateriasProfesor::class.java)
                    intent.putExtra("id",id)
                    startActivityForResult(intent,0)
                }else{
                    val intent = Intent(this, MenuMateriasAlumno::class.java)
                    intent.putExtra("id",id)
                    startActivityForResult(intent,0)
                }


            }else{
               incorrecto.text = "*Credenciales invalidas*"
            }

        }
    }

    fun validacion(id:String, pass:String,tips:Boolean):Boolean{

        if(tips){

            try {
                var maistro = DAOMaestro.getMaestro(id)
                if(maistro.contrasena.equals(pass)){
                    maestro = maistro
                    return true
                }else{
                    return false
                }
            }
            catch (e: IndexOutOfBoundsException) {
                println("NO VALIDE LA CONTRA POR LA EXPCECION MAESTRO")
                return false
            }


        }else{

            try {
                // some code
                DAOAlumnos.crearAlumnosScript()
                var alu = DAOAlumnos.getAlumno(id)
                if(alu.contrasena.equals(pass)){
                    alumno = alu
                    println("VALIDE LA CONTRA")
                    return true
                }else{

                }
                println("NO VALIDE LA CONTRA POR ERROR")
                return false
            }
            catch (e: IndexOutOfBoundsException) {
                // handler
                println("NO VALIDE LA CONTRA POR LA EXCEPCION ALUMNO")
                return false
            }

        }

    }
}
