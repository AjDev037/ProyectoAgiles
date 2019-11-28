package com.example.proyectomovilagiles.Clases

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectomovilagiles.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import dataBaseObjects.DAOMaterias
import kotlinx.android.synthetic.main.activity_tomar_asistencia.*
import objetos.Asistencia
import objetos.Clase
import objetos.Horario
import objetos.Materia

class TomarAsistencia : AppCompatActivity() {

    val WHITE = -0x1
    val BLACK = -0x1000000
    val WIDTH = 400
    val HEIGHT = 400
    var horario = Horario()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tomar_asistencia)

        val materia = intent.getSerializableExtra("materia") as Materia
        val idMat = materia.id

        horario = intent.getSerializableExtra("horarioMat") as Horario

        var idTemp = getIDFechaClase(horario, getDiaActualAsDOW())
        val horaClase = getHoraActual()

        //Esto crea el QR
        val myBitmap = encodeAsBitmap("$idMat.$idTemp.$horaClase")
        //Setea el bitmap del qr a la pantalla
        codigo.setImageBitmap(myBitmap)
    }


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
}
