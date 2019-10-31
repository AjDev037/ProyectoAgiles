package com.example.proyectomovilagiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu_materias.*

class MenuMaterias : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_materias)

        val preferencias = MyPreference(this)

        btnSalir.setOnClickListener {
            preferencias.setPass("")
            preferencias.setUser("")
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }

    }
}
