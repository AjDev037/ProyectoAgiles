package dataBaseObjects

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.FirebaseDatabase
import objetos.Alumno
import objetos.Maestro
import objetos.Materia

object DAOMaterias {

    var listaMaterias = ArrayList<Materia>()

    private fun crearMaestrosScript(){

        listaMaterias.clear()

        var materia1 = Materia("00000001","Materia1","1824", null, DAOAlumnos.getAlumnos(), DAOMaestro.getMaestro("000001111"), null, DAOHorarios.getHorario(0),DAOClases.getClases() )
        var materia2 = Materia("00000002","Materia2","1825", null, DAOAlumnos.getAlumnos(), DAOMaestro.getMaestro("000002222"), null, DAOHorarios.getHorario(1), DAOClases.getClases() )

        //agregarMaterias(materia1)
        //agregarMaterias(materia2)

        listaMaterias.add(materia1)
        listaMaterias.add(materia2)


    }

    fun getMaterias():ArrayList<Materia>{
        //TODO: Conectarse a la base de datos y regresar la lista
        crearMaestrosScript()
        return listaMaterias
    }

    fun agregarMaterias(mate:Materia){
        val database = FirebaseDatabase.getInstance()
        val referencia = database.reference.child("Materias").child(mate.id)
        referencia.setValue(mate)
    }

    fun getMateriasAlumno(id:String):ArrayList<Materia>{
        crearMaestrosScript()

        var alumno: Alumno = DAOAlumnos.getAlumno(id)
        var listaMateriasAlumno = ArrayList<Materia>()

        for(materia in listaMaterias){
            if(materia.alumnos!!.contains(alumno)){
                listaMateriasAlumno.add(materia)
            }
        }

        return listaMateriasAlumno
    }

    fun getMateriasProfesor(id:String ):ArrayList<Materia>{
        crearMaestrosScript()

        var maestro: Maestro = DAOMaestro.getMaestro(id)
        var listaMateriasMaestro = ArrayList<Materia>()

        for(materia in listaMaterias){
            if(materia.maestro!! == maestro){
                listaMateriasMaestro.add(materia)
            }
        }

        return listaMateriasMaestro
    }

    fun getMateria(id:String): Materia {
        crearMaestrosScript()
        return listaMaterias.get(listaMaterias.indexOf(Materia(id)))
    }
}