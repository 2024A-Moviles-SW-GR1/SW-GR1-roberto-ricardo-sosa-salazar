import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*
import model.Auto
import model.Parte

fun main() {
    val scanner = Scanner(System.`in`)
    val autos = Auto.cargarAutos()

    while (true) {
        mostrarMenu()
        while (!scanner.hasNextInt()) {
            println("Por favor, ingrese un número válido.")
            scanner.next()
        }
        when (scanner.nextInt()) {
            1 -> crearAuto(scanner, autos)
            2 -> crearParte(scanner, autos)
            3 -> Auto.leerAutos(autos)
            4 -> leerPartesDeAuto(scanner, autos)
            5 -> Parte.leerPartes(Parte.cargarPartes())
            6 -> actualizarAuto(scanner, autos)
            7 -> actualizarParte(scanner, autos)
            8 -> eliminarAuto(scanner, autos)
            9 -> eliminarParte(scanner, autos)
            10 -> {
                println("Gracias por preferirnos")
                break
            }
            else -> println("La opción ingresada no es válida. Por favor, intente de nuevo.")
        }
    }
}

fun mostrarMenu() {
    println("----------GESTIÓN DE AUTOS Y PARTES----------")
    println("1. Crear un nuevo Auto")
    println("2. Registrar una nueva Parte")
    println("3. Visualizar todos los Autos existentes junto con sus partes")
    println("4. Visualizar las Partes de un Auto específico")
    println("5. Visualizar todas las Partes registradas")
    println("6. Actualizar la información de un Auto")
    println("7. Actualizar la información de una Parte")
    println("8. Eliminar un Auto existente")
    println("9. Eliminar una Parte registrada")
    println("10. Finalizar y salir del Programa")
    print("Por favor, seleccione una opción del menú: ")
}

fun crearAuto(scanner: Scanner, autos: MutableList<Auto>) {
    println("Ingrese el ID del Auto:")
    while (!scanner.hasNextInt()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    val id = scanner.nextInt()
    scanner.nextLine()  // Consumir el newline
    // Comprobar si el ID ya existe
    if (autos.any { it.id == id }) {
        println("Error: Ya existe un auto con el ID proporcionado.")
        return
    }
    println("Ingrese el Modelo del Auto:")
    val modelo = scanner.nextLine()
    println("Ingrese la Marca del Auto:")
    val marca = scanner.nextLine()
    println("Ingrese el Año de Fabricación del Auto:")
    while (!scanner.hasNextInt()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    val año = scanner.nextInt()
    println("Ingrese el Precio del Auto:")
    while (!scanner.hasNextDouble()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    val precio = scanner.nextDouble()
    scanner.nextLine()  // Consumir el newline
    val nuevoAuto = Auto(id, modelo, marca, año, precio, mutableListOf())
    Auto.crearAuto(autos, nuevoAuto)
}

fun crearParte(scanner: Scanner, autos: MutableList<Auto>) {
    println("Ingrese el ID del Auto al que pertenece la Parte:")
    while (!scanner.hasNextInt()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    val autoId = scanner.nextInt()
    val auto = autos.find { it.id == autoId }
    if (auto != null) {
        println("Ingrese el ID de la Parte:")
        while (!scanner.hasNextInt()) {
            println("Por favor, ingrese un número válido.")
            scanner.next()
        }
        val id = scanner.nextInt()
        // Comprobar si el ID ya existe
        if (auto.partes.any { it.id == id }) {
            println("Error: Ya existe una parte con el ID proporcionado.")
            return
        }
        scanner.nextLine()
        println("Ingrese el Nombre de la Parte:")
        val nombre = scanner.nextLine()
        println("Ingrese el Precio de la Parte:")
        while (!scanner.hasNextDouble()) {
            println("Por favor, ingrese un número válido.")
            scanner.next()
        }
        val precio = scanner.nextDouble()
        scanner.nextLine()  // Consumir el newline
        var fechaFabricacion: LocalDate? = null
        while (fechaFabricacion == null) {
            println("Ingrese la Fecha de Fabricación de la Parte (formato AAAA-MM-DD):")
            try {
                fechaFabricacion = LocalDate.parse(scanner.next())
            } catch (e: DateTimeParseException) {
                println("Fecha no válida. Por favor, intente de nuevo.")
            }
        }
        println("Ingrese la disponibilidad de la Parte (true/false):")
        while (!scanner.hasNextBoolean()) {
            println("Por favor, ingrese un valor válido (true/false).")
            scanner.next()
        }
        val disponibilidad = scanner.nextBoolean()
        val nuevaParte = Parte(id, nombre, precio, fechaFabricacion, disponibilidad)
        Parte.crearParte(auto.partes, nuevaParte)
        Auto.guardarAutos(autos) // Guardar cambios
    } else {
        println("Auto no encontrado.")
    }
}

fun leerPartesDeAuto(scanner: Scanner, autos: MutableList<Auto>) {
    println("Ingrese el ID del Auto cuyas partes desea ver:")
    while (!scanner.hasNextInt()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    val autoId = scanner.nextInt()
    val auto = autos.find { it.id == autoId }
    if (auto != null) {
        Auto.leerPartes(auto.partes)
    } else {
        println("Auto no encontrado.")
    }
}

fun actualizarAuto(scanner: Scanner, autos: MutableList<Auto>) {
    println("Ingrese el ID del Auto a actualizar:")
    while (!scanner.hasNextInt()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    val id = scanner.nextInt()
    scanner.nextLine()  // Consumir el newline
    // Comprobar si el ID ya existe
    if (autos.none { it.id == id }) {
        println("Error: No existe un auto con el ID proporcionado.")
        return
    }
    println("Ingrese el Nuevo Modelo del Auto:")
    val modelo = scanner.nextLine()
    println("Ingrese la Nueva Marca del Auto:")
    val marca = scanner.nextLine()
    println("Ingrese el Nuevo Año de Fabricación del Auto:")
    while (!scanner.hasNextInt()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    val año = scanner.nextInt()
    println("Ingrese el Nuevo Precio del Auto:")
    while (!scanner.hasNextDouble()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    val precio = scanner.nextDouble()
    scanner.nextLine()  // Consumir el newline
    val auto = autos.find { it.id == id }
    if (auto != null) {
        if (modelo.isNotBlank() && marca.isNotBlank()) {
            auto.modelo = modelo
            auto.marca = marca
            auto.año = año
            auto.precio = precio
            Auto.guardarAutos(autos)
        } else {
            println("El modelo y la marca no pueden estar vacíos.")
        }
    }
}

fun actualizarParte(scanner: Scanner, autos: MutableList<Auto>) {
    println("Ingrese el ID del Auto al que pertenece la Parte:")
    while (!scanner.hasNextInt()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    val autoId = scanner.nextInt()
    val auto = autos.find { it.id == autoId }
    if (auto != null) {
        println("Ingrese el ID de la Parte a actualizar:")
        while (!scanner.hasNextInt()) {
            println("Por favor, ingrese un número válido.")
            scanner.next()
        }
        val id = scanner.nextInt()
        val parte = auto.partes.find { it.id == id }
        if (parte != null) {
            scanner.nextLine()  // Consumir el newline
            println("Ingrese el Nuevo Nombre de la Parte:")
            val nombre = scanner.nextLine()
            println("Ingrese el Nuevo Precio de la Parte:")
            while (!scanner.hasNextDouble()) {
                println("Por favor, ingrese un número válido.")
                scanner.next()
            }
            val precio = scanner.nextDouble()
            scanner.nextLine()  // Consumir el newline
            var fechaFabricacion: LocalDate? = null
            while (fechaFabricacion == null) {
                println("Ingrese la Nueva Fecha de Fabricación de la Parte (formato AAAA-MM-DD):")
                try {
                    fechaFabricacion = LocalDate.parse(scanner.next())
                } catch (e: DateTimeParseException) {
                    println("Fecha no válida. Por favor, intente de nuevo.")
                }
            }
            println("Ingrese la nueva disponibilidad de la Parte (true/false):")
            while (!scanner.hasNextBoolean()) {
                println("Por favor, ingrese un valor válido (true/false).")
                scanner.next()
            }
            val disponibilidad = scanner.nextBoolean()
            parte.nombre = nombre
            parte.precio = precio
            parte.fechaFabricacion = fechaFabricacion
            parte.disponibilidad = disponibilidad
            Auto.guardarAutos(autos) // Guardar cambios
        } else {
            println("Parte no encontrada.")
        }
    } else {
        println("Auto no encontrado.")
    }
}

fun eliminarAuto(scanner: Scanner, autos: MutableList<Auto>) {
    println("Ingrese el ID del Auto a eliminar:")
    while (!scanner.hasNextInt()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    val id = scanner.nextInt()
    val auto = autos.find { it.id == id }
    if (auto != null) {
        Auto.eliminarAuto(autos, auto)
    } else {
        println("Auto no encontrado.")
    }
}

fun eliminarParte(scanner: Scanner, autos: MutableList<Auto>) {
    println("Ingrese el ID del Auto al que pertenece la Parte a eliminar:")
    while (!scanner.hasNextInt()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    val autoId = scanner.nextInt()
    val auto = autos.find { it.id == autoId }
    if (auto != null) {
        println("Ingrese el ID de la Parte a eliminar:")
        while (!scanner.hasNextInt()) {
            println("Por favor, ingrese un número válido.")
            scanner.next()
        }
        val id = scanner.nextInt()
        val parte = auto.partes.find { it.id == id }
        if (parte != null) {
            Parte.eliminarParte(auto.partes, parte)
            Auto.guardarAutos(autos) // Guardar cambios
        } else {
            println("Parte no encontrada.")
        }
    } else {
        println("Auto no encontrado.")
    }
}
