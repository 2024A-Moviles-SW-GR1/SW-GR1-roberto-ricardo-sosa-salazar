package com.example.a2024swerpnd2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(contexto: Context):
    SQLiteOpenHelper(contexto,
        "Appmoviles",
        null,
        1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaAuto =
            """
                CREATE TABLE AUTO(
                    idAuto INTEGER PRIMARY KEY AUTOINCREMENT,
                    modelo VARCHAR(50),
                    marca VARCHAR(50),
                    anio INTEGER(4),
                    precio DOUBLE(2)
                )
            """.trimIndent()
        val scriptSQLCrearTablaParte =
            """
                CREATE TABLE PARTE(
                    idParte INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(100),
                    fechaFabricacion VARCHAR(10),
                    precio DOUBLE,
                    idAuto INTEGER,
                    FOREIGN KEY (idAuto) REFERENCES AUTO(idAuto)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaAuto)
        db?.execSQL(scriptSQLCrearTablaParte)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun obtenerTodosLosAutos(): ArrayList<Auto>{
        val baseDeDatosLectura = readableDatabase
        val scriptConsultaLectura = """
                SELECT * FROM AUTO
            """.trimIndent()

        val resultadoConsultaLectura = baseDeDatosLectura.rawQuery(scriptConsultaLectura,
            emptyArray()
        )
        val arregloRespuesta = arrayListOf<Auto>()
        if(resultadoConsultaLectura.moveToFirst()){
            do {
                val auto = Auto(
                    resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getString(2),
                    resultadoConsultaLectura.getInt(3),
                    resultadoConsultaLectura.getDouble(4),
                )
                arregloRespuesta.add(auto)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDeDatosLectura.close()
        return arregloRespuesta
    }

    fun crearAuto(
        modelo: String,
        marca: String,
        anio: String,
        precio: Double,
    ):Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("modelo", modelo)
        valoresAGuardar.put("marca", marca)
        valoresAGuardar.put("anio", anio)
        valoresAGuardar.put("precio", precio)
        val resultadoGuardar = baseDatosEscritura.insert(
            "AUTO", //nombre tabla
            null,
            valoresAGuardar
        )
        baseDatosEscritura.close()
        return  if(resultadoGuardar.toInt() == -1) false else true
    }

    fun actualizarAuto(
        modelo: String,
        marca: String,
        anio: String,
        precio: Double,
        idAuto: Int?,
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("modelo", modelo)
        valoresAActualizar.put("marca", marca)
        valoresAActualizar.put("anio", anio)
        valoresAActualizar.put("precio", precio.toString())
        //where: ...
        val parametrosConsultaActualizar = arrayOf(idAuto.toString())
        val resultadoActualizacion = conexionEscritura.update("AUTO",
            valoresAActualizar,
            "idAuto=?",
            parametrosConsultaActualizar)
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun eliminarAuto(idAuto: Int?): Boolean {
        val conexionEscritura = writableDatabase
        //Consulta SQL: where ... ID=? AND NOMBRE=? [?=1,?=2]
        val parametrosConsultaDelete = arrayOf(idAuto.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "AUTO",
            "idAuto=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun obtenerTodosLasPartesAuto(idAuto: Int?): ArrayList<Parte> {
        val baseDeDatosLectura = readableDatabase
        val scriptConsultaLectura = """
                SELECT * FROM PARTE WHERE idAuto = ?
            """.trimIndent()
        val arregloParametrosConsultaLectura = arrayOf(idAuto.toString())
        val resultadoConsultaLectura = baseDeDatosLectura.rawQuery(scriptConsultaLectura,
            arregloParametrosConsultaLectura)
        val existeAlmenosUno = resultadoConsultaLectura.moveToFirst()
        val arregloRespuesta = arrayListOf<Parte>()
        if(existeAlmenosUno){
            do {
                val parte = Parte(
                    resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getString(2),
                    resultadoConsultaLectura.getDouble(3),
                    resultadoConsultaLectura.getInt(4))
                arregloRespuesta.add(parte)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDeDatosLectura.close()
        return arregloRespuesta
    }

    fun eliminarParte(idParte: Int?): Boolean {
        val conexionEscritura = writableDatabase
        //Consulta SQL: where ... ID=? AND NOMBRE=? [?=1,?=2]
        val parametrosConsultaDelete = arrayOf(idParte.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "PARTE",
            "idParte=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun crearParte(nombre: String,
                   fechaFabricacion: String,
                   precio: Double,
                   idAuto: Int?): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fechaFabricacion", fechaFabricacion)
        valoresAGuardar.put("precio", precio)
        valoresAGuardar.put("idAuto", idAuto)
        val resultadoGuardar = baseDatosEscritura.insert(
            "PARTE",
            null,
            valoresAGuardar
        )
        baseDatosEscritura.close()
        return  if(resultadoGuardar.toInt() == -1) false else true
    }

    fun actualizarParte(nombre: String,
                        fechaFabricacion: String,
                        precio: Double,
                        idAuto: Int?): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fechaFabricacion", fechaFabricacion)
        valoresAActualizar.put("precio", precio)
        //where: ...
        val parametrosConsultaActualizar = arrayOf(idAuto.toString())
        val resultadoActualizacion = conexionEscritura.update("PARTE",
            valoresAActualizar,
            "idParte=?",
            parametrosConsultaActualizar)//[1]
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }
}