package dataBaseObjects

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import objetos.Maestro

object DAOMaestro {

    var listaMaestros = ArrayList<Maestro>()

     fun crearMaestrosScript(){

        val database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("Maestros")
        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    var children = p0.children


                    for (child in children) {
                        var maestro = child.getValue(Maestro::class.java)
                        listaMaestros.add(maestro!!)
                    }
                }
            }
        })

    }

    fun getMaestros():ArrayList<Maestro>{
        //TODO: Conectarse a la base de datos y regresar la lista
        crearMaestrosScript()
        return listaMaestros
    }



    fun agregarMaestro(profe:Maestro){
        val database = FirebaseDatabase.getInstance()
        val referencia = database.reference.child("Maestros").child(profe.id)
        referencia.setValue(profe)
    }

    fun getMaestro(id:String): Maestro {
        crearMaestrosScript()
        return listaMaestros.get(listaMaestros.indexOf(Maestro(id)))
    }
}