package com.example.a2024swerpnd2

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var listaAutos:ArrayList<Auto> = arrayListOf()
    private var adapter:ArrayAdapter<Auto>? = null
    private var posicionitemSeleccionadoMenu: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BaseDeDatos.dbHelper = SQLiteHelper(this)

        val listaAutosMain = findViewById<ListView>(R.id.id_lista_auto)
        listaAutos = BaseDeDatos.dbHelper!!.obtenerTodosLosAutos()


        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listaAutos)
        listaAutosMain.adapter = adapter
        adapter!!.notifyDataSetChanged()
        val botonCrear = findViewById<Button>(R.id.btn_crear_auto)
        botonCrear.setOnClickListener {
            irActividad(CrearAutoActivity::class.java)
        }
        registerForContextMenu(listaAutosMain)

        val BotonGMaps = findViewById<Button>(R.id.btn_maps)
        BotonGMaps.setOnClickListener {
            irActividad(GGoogleMapsActivity::class.java)
        }

    }
    fun irActividad(clase: Class<*>, autoSeleccionado: Auto? = null) {
        val intent = Intent(this, clase)
        if(autoSeleccionado != null){
            intent.putExtra("auto", autoSeleccionado)
        }
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //llenar las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_auto, menu)
        //Obtener id
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionitemSeleccionadoMenu = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.editar_auto_menu -> {
                val autoSeleccionado: Auto? = obtenerAutoSeleccionado(posicionitemSeleccionadoMenu)
                irActividad(CrearAutoActivity::class.java, autoSeleccionado)
                return true
            }

            R.id.eliminar_auto_menu -> {
                abrirDialogo()//
                return true
            }
            R.id.partes_auto_menu -> {
                val autoSeleccionado: Auto? = obtenerAutoSeleccionado(posicionitemSeleccionadoMenu)
                irActividad(ListaParte::class.java, autoSeleccionado)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun obtenerAutoSeleccionado(posicionAutoSeleccionadoMenu: Int): Auto? {
        return if(posicionAutoSeleccionadoMenu > -1) listaAutos[posicionAutoSeleccionadoMenu] else null
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea Eliminar el auto?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog, which ->
                val autoSeleccionado: Auto? = obtenerAutoSeleccionado(posicionitemSeleccionadoMenu)
                val respuesta = BaseDeDatos.dbHelper!!.eliminarAuto(
                    autoSeleccionado?.idAuto
                )
                if (respuesta){
                    listaAutos = BaseDeDatos.dbHelper!!.obtenerTodosLosAutos()
                    // Actualizar el adaptador con la nueva lista de clientes
                    adapter?.clear() // Limpiar los datos antiguos del adaptador
                    adapter?.addAll(listaAutos) // Agregar los nuevos datos
                    adapter?.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
                    mostrarSnackbar("Auto eliminado!")
                }
            }
        )

        builder.setNegativeButton("Cancelar", null)
        builder.create().show()

    }

    private fun mostrarSnackbar(mensaje: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_main_activity),
            mensaje,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }


}