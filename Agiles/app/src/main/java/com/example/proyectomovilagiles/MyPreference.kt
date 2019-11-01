package com.example.proyectomovilagiles

import android.content.Context

class MyPreference(context: Context){

    val ID = "usuario"
    val PASS = "Pass"

    val preference = context.getSharedPreferences(ID,Context.MODE_PRIVATE)

    fun getUser():String?{
        var id = preference.getString(ID,"")
        return id
    }

    fun setUser(id:String){
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

}