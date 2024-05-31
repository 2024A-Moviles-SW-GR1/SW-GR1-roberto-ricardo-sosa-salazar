package model

import java.io.*

data class Auto(
    var id: Int,
    var modelo: String,
    var marca: String,
    var año: Int,
    var precio: Double,
    var partes: MutableList<Parte>
) : Serializable {
    companion object {
        const val serialVersionUID = 1L

        fun cargarAutos(): MutableList<Auto> {
            return try {
                ObjectInputStream(FileInputStream("src/main/kotlin/model/autos.txt")).use { it.readObject() as MutableList<Auto> }
            } catch (e: Exception) {
                mutableListOf()
            }
        }

        fun guardarAutos(autos: List<Auto>) {
            ObjectOutputStream(FileOutputStream("src/main/kotlin/model/autos.txt")).use { it.writeObject(autos) }
        }

        fun crearAuto(autos: MutableList<Auto>, auto: Auto) {
            autos.add(auto)
            guardarAutos(autos)
        }

        fun eliminarAuto(autos: MutableList<Auto>, auto: Auto) {
            autos.remove(auto)
            guardarAutos(autos)
        }

        fun leerAutos(autos: List<Auto>) {
            if (autos.isEmpty()) {
                println("No hay autos registrados.")
            } else {
                for (auto in autos) {
                    println(auto)
                    leerPartes(auto.partes)
                }
            }
        }

        fun leerPartes(partes: List<Parte>) {
            if (partes.isEmpty()) {
                println("No hay partes registradas para este auto.")
            } else {
                for (parte in partes) {
                    println(parte)
                }
            }
        }
    }
}
