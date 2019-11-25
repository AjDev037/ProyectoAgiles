package com.example.proyectomovilagiles.Preferencias

import android.content.Context

class MyPreference(context: Context){

    val ID = "usuario"
    val PASS = "Pass"
    val TIPO = "tipoMaestro"

    val preference = context.getSharedPreferences(ID,Context.MODE_PRIVATE)

    fun getId():String?{
        var id = preference.getString(ID,"")
        return id
    }

    fun setId(id:String){
        val editor = preference.edit()
        editor.putString(ID,id)
        editor.apply()
    }

    fun getPass():String?{
        return preference.getString(PASS,"")
    }

    fun setPass(pass:String){
        val editor = preference.edit()
        editor.putString(PASS,pass)
        editor.apply()
    }

    fun setTipo(tipo:Boolean){
        val editor = preference.edit()
        editor.putBoolean(TIPO,tipo)
        editor.apply()
    }

    fun getTipo():Boolean?{
        return preference.getBoolean(TIPO,false)
    }

}