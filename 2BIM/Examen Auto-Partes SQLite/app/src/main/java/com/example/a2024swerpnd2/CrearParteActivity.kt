package com.example.a2024swerpnd2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class CrearParteActivity : AppCompatActivity() {
    private var parte: Parte? = null
    private var auto: Auto? = null
    private var esNuevaParte: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_parte)

        parte = intent.getParcelableExtra("parte")
        auto =intent.getParcelableExtra("auto")

        esNuevaParte = parte == null

        configurarFormulario()

        val nombre = findViewById<EditText>(R.id.id_nombre_parte)
        val fecha = findViewById<EditText>(R.id.id_fecha_parte)
        val precio = findViewById<EditText>(R.id.id_precio)
        val botonGuardar = findViewById<Button>(R.id.btn_crear_parte_ac)


        botonGuardar.setOnClickListener {
            if (esNuevaParte){
                val respuesta = BaseDeDatos.dbHelper!!.crearParte(
                    nombre.text.toString(),
                    fecha.text.toString(),
                    precio.text.toString().toDouble(),
                    auto!!.idAuto)
                if (respuesta){
                    irActividad(ListaParte::class.java)
                    mostrarSnackbar("Parte creada!")
                }else{mostrarSnackbar("Parte no creada!")}
            }else{
                val respuesta = BaseDeDatos.dbHelper!!.actualizarParte(nombre.text.toString(),
                    fecha.text.toString(),
                    precio.text.toString().toDouble(),
                    parte?.idParte)

                if (respuesta){
                    irActividad(ListaParte::class.java)
                }else{mostrarSnackbar("Parte no actualizada!")}
            }
        }

    }

    private fun configurarFormulario() {
        val nombre = findViewById<EditText>(R.id.id_nombre_parte)
        val fecha = findViewById<EditText>(R.id.id_fecha_parte)
        val precio = findViewById<EditText>(R.id.id_precio)
        val botonGuardar = findViewById<Button>(R.id.btn_crear_parte_ac)
        val titulo = findViewById<TextView>(R.id.titulo_crear_parte_auto)

        if (esNuevaParte) {
            botonGuardar.setText("Crear")
            titulo.setText("Crear Parte")
        } else {
            botonGuardar.setText("Actualizar")
            titulo.setText("Actualizar Parte")
            // Configura el formulario para editar un pedido existente
            nombre.setText(parte!!.nombre)
            fecha.setText(parte!!.fechaFabricacion.toString())
            precio.setText(parte!!.precio.toString())
        }
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    private fun mostrarSnackbar(mensaje: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_crear_parte),
            mensaje,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}