package com.example.proyectomovilagiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.google.zxing.qrcode.encoder.QRCode
import android.graphics.Bitmap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.WriterException
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import dataBaseObjects.DAOClases
import kotlinx.android.synthetic.main.activity_generar_clase.*
import objetos.Asistencia
import objetos.Clase
import objetos.Dia
import objetos.Horario


class GenerarClase : AppCompatActivity() {

    val WHITE = -0x1
    val BLACK = -0x1000000
    val WIDTH = 400
    val HEIGHT = 400
    var horario = Horario()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generar_clase)
        var salon = intent.getStringExtra("salon")
        println("SALON: $salon")
        val idMat = intent.getStringExtra("idMat")
        println("MATERIA: $idMat")
        horario = intent.getSerializableExtra("horarioMat") as Horario
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
        var clase = Clase(idTemp!!, getDiaFromHorario(horario, getDiaActualAsDOW())!!, getFechaActual(),ArrayList<Asistencia>(),salon!!)
        DAOClases.crearClase(clase,idMat)

        //Esto crea el QR
        val myBitmap = encodeAsBitmap("$idMat.$idTemp.1")
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
