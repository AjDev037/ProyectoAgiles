package com.example.proyectomovilagiles

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import objetos.Materia
import objetos.MateriaBD

val NOMBRE_BD = "MateriasDB"
val NOMBRE_TABLA = "Materias"
val COL_MAT = "Materia"
val COL_HORA = "Hora"
val COL_DIA = "Dia"


class DbHandler(context: Context):SQLiteOpenHelper(context, NOMBRE_BD,null,1){


    override fun onCreate(p0: SQLiteDatabase?) {

        val tabla = "CREATE TABLE $NOMBRE_TABLA ($COL_MAT VARCHAR(50), $COL_HORA VARCHAR(50), $COL_DIA VARCHAR(50))"
        p0?.execSQL(tabla)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarDatos(dia:String, hora:String, nombre:String){


        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_MAT,nombre)
        cv.put(COL_DIA,dia)
        cv.put(COL_HORA,hora)
        db.insert(NOMBRE_TABLA,null,cv)
    }

    fun leerDatos():ArrayList<MateriaBD>{
        var lista = ArrayList<MateriaBD>()

        var db = this.readableDatabase
        val query = "SELECT * FROM $NOMBRE_TABLA"
        var result :Cursor? = null
        try {
            result = db.rawQuery(query,null)
            if(result!!.moveToFirst()){
                do{
                    var materia = MateriaBD()
                    materia.nombre = result.getString(result.getColumnIndex(COL_MAT))
                    materia.hora = result.getString(result.getColumnIndex(COL_HORA))
                    materia.dia = result.getString(result.getColumnIndex(COL_DIA))
                    lista.add(materia)
                } while(result.moveToNext())

            }
        } finally {
            // this gets called even if there is an exception somewhere above
            result?.close()
        }

        return lista
    }

    fun borrarDatos(){
        val db = this.writableDatabase
        db.delete(NOMBRE_TABLA,null,null)
        db.execSQL("DELETE FROM $NOMBRE_TABLA")
        //db.execSQL("TRUNCATE table" + NOMBRE_TABLA)
        db.close()
    }


}