package dataBaseObjects

import objetos.Maestro

object DAOMaestro {

    var listaMaestros = ArrayList<Maestro>()

    private fun crearMaestrosScript(){

        listaMaestros.clear()

        var maestro1 = Maestro("Maestro1","000001111","contrasena1")
        var maestro2 = Maestro("Maestro2","000002222","contrasena2")
        var maestro3 = Maestro("Maestro3","000003333","contrasena3")
        var maestro4 = Maestro("Maestro4","000004444","contrasena4")
        var maestro5 = Maestro("Maestro5","000005555","contrasena5")

        listaMaestros.add(maestro1)
        listaMaestros.add(maestro2)
        listaMaestros.add(maestro3)
        listaMaestros.add(maestro4)
        listaMaestros.add(maestro5)


    }

    fun getMaestros():ArrayList<Maestro>{
        //TODO: Conectarse a la base de datos y regresar la lista
        crearMaestrosScript()
        return listaMaestros
    }

    fun getMaestro(id:String): Maestro {
        crearMaestrosScript()
        return listaMaestros.get(listaMaestros.indexOf(Maestro(id)))
    }
}