package com.example.a2024swerpnd2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class CrearAutoActivity : AppCompatActivity() {
    private var auto: Auto? = null
    private var esNuevoAuto: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_auto)


        auto = intent.getParcelableExtra("auto")
        esNuevoAuto = auto == null

        configurarFormulario()

        val modelo = findViewById<EditText>(R.id.id_modelo_auto)
        val marca = findViewById<EditText>(R.id.id_marca_auto)
        val anio = findViewById<EditText>(R.id.id_anio_auto)
        val precio = findViewById<EditText>(R.id.id_precio_auto)
        val botonCrear = findViewById<Button>(R.id.btn_crear_auto)


        botonCrear.setOnClickListener {
            if (esNuevoAuto){
                val respuesta = BaseDeDatos.dbHelper!!.crearAuto(modelo.text.toString(),
                    marca.text.toString(),
                    anio.text.toString(),
                    precio.text.toString().toDouble(),
                )

                if (respuesta){
                    irActividad(MainActivity::class.java)
                }else{mostrarSnackbar("Auto no creado!")}
            }else{
                val respuesta = BaseDeDatos.dbHelper!!.actualizarAuto(
                    modelo.text.toString(),
                    marca.text.toString(),
                    anio.text.toString(),
                    precio.text.toString().toDouble(),
                    auto?.idAuto)

                if (respuesta){
                    irActividad(MainActivity::class.java)
                }else{mostrarSnackbar("Cliente no actualizado!")}
            }
        }

    }

    private fun configurarFormulario() {
        val modelo = findViewById<EditText>(R.id.id_modelo_auto)
        val marca = findViewById<EditText>(R.id.id_marca_auto)
        val anio = findViewById<EditText>(R.id.id_anio_auto)
        val precio = findViewById<EditText>(R.id.id_precio_auto)
        val botonCrear = findViewById<Button>(R.id.btn_crear_auto)
        val titulo = findViewById<TextView>(R.id.titulo_crear_auto)

        if (esNuevoAuto) {
            botonCrear.setText("Crear")
            titulo.setText("Crear Auto")
        } else {
            botonCrear.setText("Actualizar")
            titulo.setText("Actualizar Auto")

            // Configura el formulario para editar un cliente existente
            modelo.setText(auto?.modelo)
            marca.setText(auto?.marca)
            anio.setText(auto?.anio.toString())
            precio.setText(auto?.precio.toString())

        }
    }

    private fun irActividad(clase: Class<*>) {
            val intent = Intent(this, clase)
            startActivity(intent)
    }

    private fun mostrarSnackbar(mensaje: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_crear_auto),
            mensaje,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}