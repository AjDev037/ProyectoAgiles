package com.example.proyectomovilagiles.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectomovilagiles.Materias.MenuMateriasAlumno
import com.example.proyectomovilagiles.Materias.MenuMateriasProfesor
import com.example.proyectomovilagiles.Preferencias.MyPreference
import com.example.proyectomovilagiles.R
import dataBaseObjects.DAOAlumnos
import dataBaseObjects.DAOMaestro
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_login.*
import objetos.Alumno
import objetos.Maestro
import objetos.Observer
import java.lang.Exception
import java.lang.IndexOutOfBoundsException

class Login : AppCompatActivity(), Observer {

    //Booleano que muestra donde buscara los datos la app,
    // si es falso es para alumnos, si es verdadero es para profesores.
    private var tipoMaestro: Boolean = false
    private var maestro = Maestro()
    var alumno = Alumno()

    private var pass: String? = null
    var id: String? = null

    private var validacionAutomatica: Boolean = false

    private var preferencias:MyPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        preferencias = MyPreference(this)

        DAOMaterias.limpiar()
        DAOMaterias.crearMateriasScript()

        if (preferencias!!.getId() != "" && preferencias!!.getPass() != "") {

            id = preferencias!!.getId()
            pass = preferencias!!.getPass()

            tipoMaestro = preferencias!!.getTipo()!!

            validacionAutomatica = true

            if (tipoMaestro) {
                DAOMaestro.observadores.add(this)
                DAOMaestro.crearMaestrosScript()
            } else {
                DAOAlumnos.observadores.add(this)
                DAOAlumnos.crearAlumnosScript()
            }
        }

        btnTipo.setOnClickListener {
            if (tipoMaestro) {
                DAOAlumnos.observadores.add(this)
                DAOMaestro.observadores.remove(this)

                btnTipo.text = "Cambiar a Profesor"
                tipoMaestro = false
                println(tipoMaestro)

            } else {
                DAOMaestro.observadores.add(this)
                DAOAlumnos.observadores.remove(this)

                btnTipo.text = "Cambiar a Alumno"
                tipoMaestro = true
                println(tipoMaestro)

            }
        }

        btnLogin.setOnClickListener {
            id = txtUsuario.text.toString()
            pass = txtPass.text.toString()

            if (tipoMaestro) {
                DAOMaestro.crearMaestrosScript()
            } else {
                DAOAlumnos.crearAlumnosScript()
            }
        }
    }

    fun terminarLogin() {
        if (validacion(this.id!!, this.pass!!, tipoMaestro)) {

            preferencias!!.setId(this.id!!)
            preferencias!!.setPass(this.pass!!)
            preferencias!!.setTipo(tipoMaestro)

            if (tipoMaestro) {
                val intent = Intent(this, MenuMateriasProfesor::class.java)
                intent.putExtra("id", id)

                try{
                    DAOAlumnos.observadores.remove(this)
                    DAOMaestro.observadores.remove(this)
                }catch (e:Exception){
                    println("No se pudo remover\n" + e.message)
                }

                startActivityForResult(intent, 0)

            } else {
                val intent = Intent(this, MenuMateriasAlumno::class.java)

                try{
                    DAOAlumnos.observadores.remove(this)
                    DAOMaestro.observadores.remove(this)
                }catch (e:Exception){
                    println("No se pudo remover\n" + e.message)
                }

                intent.putExtra("alumno", alumno)
                startActivityForResult(intent, 0)
            }

        } else {
            incorrecto.text = "*Credenciales invalidas*"
        }
    }

    fun validarLoginA(id: String, pass: String) {

        this.id = id
        this.pass = pass

        for (i in DAOAlumnos.getAlumnos()) {
            if (i.id == (this.id) && alumno.contrasena == this.pass) {
                val intent = Intent(this, MenuMateriasAlumno::class.java)
                intent.putExtra("alumno", alumno)

                DAOAlumnos.observadores.remove(this)
                DAOMaestro.observadores.remove(this)

                startActivityForResult(intent, 0)
            }
        }
    }

    fun validarLoginM(id: String, pass: String) {

        this.id = id
        this.pass = pass

        for (i in DAOMaestro.getMaestros()) {
            if (i.id == (this.id) && i.contrasena == this.pass) {
                val intent = Intent(this, MenuMateriasProfesor::class.java)
                intent.putExtra("id", id)

                DAOAlumnos.observadores.remove(this)
                DAOMaestro.observadores.remove(this)

                startActivityForResult(intent, 0)
            }
        }
    }

    fun validacion(id: String, pass: String, tipo: Boolean): Boolean {

        if (tipo) {
            try {
                var maistro = DAOMaestro.getMaestro(id)
                if (maistro.contrasena.equals(pass)) {
                    maestro = maistro
                    return true
                } else {
                    return false
                }
            } catch (e: IndexOutOfBoundsException) {
                println("NO VALIDE LA CONTRA POR LA EXPCECION MAESTRO")
                return false
            }


        } else {
            try {
                // some code
                DAOAlumnos.crearAlumnosScript()
                var alu = DAOAlumnos.getAlumno(id)
                if (alu.contrasena.equals(pass)) {
                    alumno = alu
                    println("VALIDE LA CONTRA")
                    return true
                } else {

                }
                println("NO VALIDE LA CONTRA POR ERROR")
                return false
            } catch (e: IndexOutOfBoundsException) {
                // handler
                println("NO VALIDE LA CONTRA POR LA EXCEPCION ALUMNO")
                return false
            }

        }

    }

    override fun notificar(name: String) {
        if (validacionAutomatica) {
            if (tipoMaestro) {
                validarLoginM(this.id!!, this.pass!!)
            } else {
                validarLoginA(this.id!!, this.pass!!)
            }
        } else {
            terminarLogin()
        }
    }
}
