package com.example.proyectomovilagiles.legacy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectomovilagiles.*
import com.example.proyectomovilagiles.Asistencia.TomarAsistencia
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_generar_clase.*
import objetos.*


class GenerarClaseLegacy : AppCompatActivity() {

    var horario = Horario()
    var idMat = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generar_clase)

        var salon = intent.getStringExtra("salon")
        println("SALON: $salon")

        val materia = intent.getSerializableExtra("materia") as Materia
        idMat = materia.id
        println("MATERIA: $idMat")

        horario = intent.getSerializableExtra("horarioMat") as Horario
        println(horario.dias)

        if(horario == null) {
            println("HORAR NULO")
        }
        var idTemp = getIDFechaClase(horario, getDiaActualAsDOW())

        if(idTemp == null){
            println("ID CLASE NULO")
        }

        if(getDiaActualAsDOW() == null){
            println("EL DIA ACTUAL ES NULO")
        }
        
        var clase = Clase(idTemp!!, getDiaFromHorario(horario, getDiaActualAsDOW())!!, getFechaActual(),ArrayList(),salon!!, "",ArrayList())
        materia.clases.add(clase)

        DAOMaterias.agregarMaterias(materia)

        btnTomarAsist.setOnClickListener{
            generarQR()
        }
    }

    fun generarQR(){
        val intent = Intent(this, TomarAsistencia::class.java)
            intent.putExtra("horarioMat",horario)
            intent.putExtra("materia",idMat)
        startActivityForResult(intent,0)
    }

    /*
    @Throws(WriterException::class)
    fun encodeAsBitmap(str: String): Bitmap? {
        val result: BitMatrix
        try {
            result = MultiFormatWriter().encode(
                str,
                BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null
            )
        } catch (iae: IllegalArgumentException) {
            // Unsupported format
            return null
        }

        val w = result.width
        val h = result.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                pixels[offset + x] = if (result.get(x, y)) BLACK else WHITE
            }
        }

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
        return bitmap
    }
    */
}
