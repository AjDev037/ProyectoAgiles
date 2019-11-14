package dataBaseObjects

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import objetos.Alumno
import objetos.Maestro
import objetos.Materia

object DAOMaterias {

    var listaMaterias = ArrayList<Materia>()

     fun crearMaestrosScript(){



        val database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("Materias")
        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    var children = p0.children


                    for (child in children) {
                        var materia = child.getValue(Materia::class.java)
                        //println(materia!!.nombre)
                        listaMaterias.add(materia!!)
                    }
                }
            }
        })

 /*        listaMaterias.clear()

         var materia1 = Materia("00000001","Metodos Agiles","1824", null, DAOAlumnos.getAlumnos(),
             DAOMaestro.getMaestro("000001111"), null, DAOHorarios.getHorario(0),DAOClases.getClases() )
         var materia2 = Materia("00000002","Arquitectura","1825", null, DAOAlumnos.getAlumnos(),
             DAOMaestro.getMaestro("000001111"), null, DAOHorarios.getHorario(1), DAOClases.getClases() )
         var materia3 = Materia("00000003","Redes","1826", null, DAOAlumnos.getAlumnos(),
             DAOMaestro.getMaestro("000002222"), null, DAOHorarios.getHorario(0),DAOClases.getClases() )
         var materia4 = Materia("00000004","Seguridad Informatica","1827", null, DAOAlumnos.getAlumnos(),
             DAOMaestro.getMaestro("000002222"), null, DAOHorarios.getHorario(1), DAOClases.getClases() )
         var materia5 = Materia("00000005","Materia1","1828", null, DAOAlumnos.getAlumnos(),
             DAOMaestro.getMaestro("000003333"), null, DAOHorarios.getHorario(0),DAOClases.getClases() )
         var materia6 = Materia("00000006","Materia2","1829", null, DAOAlumnos.getAlumnos(),
             DAOMaestro.getMaestro("000003333"), null, DAOHorarios.getHorario(1), DAOClases.getClases() )
         var materia7 = Materia("00000007","Materia1","1834", null, DAOAlumnos.getAlumnos(),
             DAOMaestro.getMaestro("000004444"), null, DAOHorarios.getHorario(0),DAOClases.getClases() )
         var materia8 = Materia("00000008","Materia2","1835", null, DAOAlumnos.getAlumnos(),
             DAOMaestro.getMaestro("000004444"), null, DAOHorarios.getHorario(1), DAOClases.getClases() )
         var materia9 = Materia("00000009","Materia1","1835", null, DAOAlumnos.getAlumnos(),
             DAOMaestro.getMaestro("000005555"), null, DAOHorarios.getHorario(0),DAOClases.getClases() )
         var materia0 = Materia("00000010","Materia2","1837", null, DAOAlumnos.getAlumnos(),
             DAOMaestro.getMaestro("000005555"), null, DAOHorarios.getHorario(1), DAOClases.getClases() )

         agregarMaterias(materia1)
         agregarMaterias(materia2)
         agregarMaterias(materia3)
         agregarMaterias(materia4)
         agregarMaterias(materia5)
         agregarMaterias(materia6)
         agregarMaterias(materia7)
         agregarMaterias(materia8)
         agregarMaterias(materia9)
         agregarMaterias(materia0)

         listaMaterias.add(materia1)
         listaMaterias.add(materia2)*/

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

    fun limpiar(){
        listaMaterias.clear()
    }
}
