package com.example.proyectomovilagiles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import objetos.Materia

val NOMBRE_BD = "MateriasDB"
val NOMBRE_TABLA = "Materias"
val COL_MAT = "Materia"
val COL_HORA = "Hora"
val COL_DIA = "Dia"


class DbHandler(context: Context):SQLiteOpenHelper(context, NOMBRE_BD,null,1){


    override fun onCreate(p0: SQLiteDatabase?) {

        val createTable = "CREATE TABLE $NOMBRE_BD ($COL_MAT VARCHAR(50), $COL_HORA VARCHAR(50), $COL_DIA VARCHAR(50)"

        p0?.execSQL(createTable)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(materia:Materia){
        val db = this.writableDatabase
        var cv = ContentValues()
        //cv.put(CO)
    }

}