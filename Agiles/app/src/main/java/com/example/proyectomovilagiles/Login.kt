package com.example.proyectomovilagiles

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val preferencias = MyPreference(this)

        val intent = Intent(this, MenuMaterias::class.java)

        if(preferencias.getUser() != "" && preferencias.getPass() != ""){

            var id = preferencias.getUser()
            var contra = preferencias.getPass()
            if(validacion(id!!,contra!!)){
                startActivity(intent)
            }
        }

        btnLogin.setOnClickListener {
            var id = txtUsuario.text.toString()
            var pass = txtPass.text.toString()
            if(validacion(id,pass)){
                preferencias.setUser(id)
                preferencias.setPass(pass)
                startActivity(intent)
            }else{
               incorrecto.text = "*Credenciales invalidas*"
            }

        }
    }

    fun validacion(id:String, pass:String):Boolean{

        if(id.equals("00000164788") && pass.equals("12345")){

            return true
        }

        return false
    }
}
