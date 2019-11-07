package objetos

import java.io.Serializable

class Maestro(var nombre: String, var id: String, var contrasena: String) : Serializable {

    constructor(id: String) : this("", id, "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Maestro

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}