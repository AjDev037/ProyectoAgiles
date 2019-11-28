package com.example.proyectomovilagiles.SQL

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


val NOM_DB = "MATERIAS"
val NOM_TAB = "HORARIO_CLASE"
val COL_CLA = "Clase"
val COL_DIA = "Dia"
val COL_HOR = "Horario"

class DbHandler(context:Context):SQLiteOpenHelper(context, NOM_DB,null,1){

    override fun onCreate(p0: SQLiteDatabase?) {
        val tabla = "CREATE TABLE $NOM_TAB ($COL_CLA VARCHAR(50), $COL_HOR VARCHAR(50), $COL_DIA VARCHAR(50))"
        p0?.execSQL(tabla)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertarDatos(dia:String, hora:String, nombre:String){


        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_CLA,nombre)
        cv.put(COL_DIA,dia)
        cv.put(COL_HOR,hora)
        db.insert(NOM_TAB,null,cv)
    }

}

