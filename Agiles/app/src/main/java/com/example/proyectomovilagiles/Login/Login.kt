package com.example.proyectomovilagiles.Login

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.proyectomovilagiles.Materias.MenuMateriasAlumno
import com.example.proyectomovilagiles.Materias.MenuMateriasProfesor
import com.example.proyectomovilagiles.Preferencias.MyPreference
import com.example.proyectomovilagiles.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    val PERMISO_STORAGE = 1923
    val PERMISO_CAMARA = 1278


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),PERMISO_STORAGE)

        }

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA),PERMISO_STORAGE)

        }

        DAOMaterias.limpiar()
        DAOMaestro.crearMaestrosScript()
        DAOAlumnos.crearAlumnosScript()
        DAOMaterias.crearMateriasScript()

        val preferencias = MyPreference(this)
        //preferencias.setVacio(true)

        //val intent = Intent(this, MenuMateriasProfesor::class.java)

        if(preferencias.getId() != "" && preferencias.getPass() != ""){

            var id = preferencias.getId()
            var contra = preferencias.getPass()
            tipo = preferencias.getTipo()!!
            println("HOOOOOOOOOOOOOLLLLLLLLLLLLLAAAAAAAAAAAAAAAAA")
            println(id)
            println(contra)
            println(tipo)
            if(tipo){
                println("VALIDACION AUTOMATICA")
                validarLoginM(id!!,contra!!)
            }else{
                validarLoginA(id!!,contra!!)
            }

        }

        btnTipo.setOnClickListener{
            if(tipo){
                btnTipo.text = "Cambiar a Profesor"
                tipo = false
                println(tipo)
            }else{
                btnTipo.text = "Cambiar a Alumno"
                tipo = true
                println(tipo)
            }
        }

        btnTomarAsist.setOnClickListener {
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

                    intent.putExtra("alumno",alumno)
                    startActivityForResult(intent,0)
                }


            }else{
                incorrecto.text = "*Credenciales invalidas*"
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        DAOMaterias.limpiar()
        DAOMaestro.limpiar()
        DAOAlumnos.limpiar()
        DAOMaestro.crearMaestrosScript()
        DAOAlumnos.crearAlumnosScript()
        DAOMaterias.crearMateriasScript()
    }

    fun validarLoginA(id:String, pass:String){
        val context = this
        val database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("Alumnos")
        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    var children = p0.children

                    for (child in children) {
                        var alumno = child.getValue(Alumno::class.java)
                        if(alumno?.id.equals((id)) && alumno?.contrasena.equals(pass)){
                            val intent = Intent(context, MenuMateriasAlumno::class.java)
                            intent.putExtra("alumno",alumno)
                            startActivityForResult(intent,0)
                        }
                    }
                }
            }
        })
    }

    fun validarLoginM(id:String, pass:String){
        val context = this
        val database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("Maestros")
        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    var children = p0.children

                    for (child in children) {
                        var profe = child.getValue(Maestro::class.java)
                        println("ESTE ES EL PROFE TEMPORAL")
                        println(profe!!.id)
                        println(profe.contrasena)
                        if(profe?.id == (id) && profe?.contrasena == pass){
                            println("ENTRE A LA VALIDACION DEL MAESTRO AUTOMATICA")
                            val intent = Intent(context, MenuMateriasProfesor::class.java)
                            intent.putExtra("id",id)
                            startActivityForResult(intent,0)
                        }
                    }
                }
            }
        })
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISO_STORAGE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permiso concedido", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Permiso negado", Toast.LENGTH_LONG).show()
            }
        }else if(requestCode == PERMISO_CAMARA){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permiso concedido", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Permiso negado", Toast.LENGTH_LONG).show()
            }
        }
    }

}
