package dataBaseObjects

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import objetos.Alumno
import objetos.Observer

object DAOAlumnos {

    var observadores = ArrayList<Observer>()
    var listaAlumnos = ArrayList<Alumno>()

    fun crearAlumnosScript(){

        val database = FirebaseDatabase.getInstance()

        val referencia = database.getReference("Alumnos")
        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    var children = p0.children


                    for (child in children) {
                        var alumno = child.getValue(Alumno::class.java)
                        println(alumno!!.nombre)
                        listaAlumnos.add(alumno)
                    }
                }

                notificar()
            }
        })
    }

    fun getAlumnos():ArrayList<Alumno>{
        //TODO: Conectarse a la base de datos y regresar la lista
        //crearAlumnosScript()
        while (listaAlumnos.isEmpty()){
            crearAlumnosScript()
            if(listaAlumnos.isNotEmpty()){
                break
            }
        }
        return listaAlumnos
    }

    fun agregarAlumno(al:Alumno){
        val database = FirebaseDatabase.getInstance()
        val referencia = database.reference.child("Alumnos").child(al.id)
        referencia.setValue(al)
    }

    fun getAlumno(id:String): Alumno {
        crearAlumnosScript()

        return listaAlumnos[listaAlumnos.indexOf(Alumno(id))]
    }

    fun limpiar(){
        listaAlumnos.clear()
    }

    fun notificar(){
        for(i in observadores){
            i.notificar("Alumnos")
        }
    }
}