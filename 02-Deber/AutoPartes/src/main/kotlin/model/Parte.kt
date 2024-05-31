package model

import java.io.*
import java.time.LocalDate

data class Parte(
    var id: Int,
    var nombre: String,
    var precio: Double,
    var fechaFabricacion: LocalDate,
    var disponibilidad: Boolean
) : Serializable {
    companion object {
        const val serialVersionUID = 1L

        fun cargarPartes(): MutableList<Parte> {
            return try {
                ObjectInputStream(FileInputStream("src/main/kotlin/data/partes.txt")).use { it.readObject() as MutableList<Parte> }
            } catch (e: Exception) {
                mutableListOf()
            }
        }

        fun guardarPartes(partes: List<Parte>) {
            ObjectOutputStream(FileOutputStream("src/main/kotlin/data/partes.txt")).use { it.writeObject(partes) }
        }

        fun crearParte(partes: MutableList<Parte>, parte: Parte) {
            partes.add(parte)
            guardarPartes(partes)
        }

        fun eliminarParte(partes: MutableList<Parte>, parte: Parte) {
            partes.remove(parte)
            guardarPartes(partes)
        }

        fun leerPartes(partes: List<Parte>) {
            if (partes.isEmpty()) {
                println("No hay partes registradas.")
            } else {
                for (parte in partes) {
                    println(parte)
                }
            }
        }
    }
}
