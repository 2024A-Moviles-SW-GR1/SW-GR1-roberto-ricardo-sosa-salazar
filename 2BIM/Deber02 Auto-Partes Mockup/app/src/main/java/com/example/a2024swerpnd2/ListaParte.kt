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
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class ListaParte : AppCompatActivity() {

    private var auto: Auto? = null
    private var listaPartes:ArrayList<Parte> = arrayListOf()
    private var adapter: ArrayAdapter<Parte>? = null
    private var posicionitemSeleccionadoMenu: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_parte)


        auto = intent.getParcelableExtra("auto")
        BaseDeDatos.dbHelper = SQLiteHelper(this)
        findViewById<TextView>(R.id.id_texto_parte_auto).setText("Parte del auto ${auto?.modelo}")

        val listaParteAuto = findViewById<ListView>(R.id.id_lista_parte_auto)
        listaPartes = BaseDeDatos.dbHelper!!.obtenerTodosLasPartesAuto(auto!!.idAuto)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listaPartes)
        listaParteAuto.adapter = adapter
        adapter!!.notifyDataSetChanged()
        val botonCrear = findViewById<Button>(R.id.btn_crear_parte_ac)
        botonCrear.setOnClickListener {
            irActividad(CrearParteActivity::class.java, auto)
        }
        registerForContextMenu(listaParteAuto)
    }
    fun irActividad(clase: Class<*>, auto:Auto? = null, parte:Parte? = null) {
        val intent = Intent(this, clase)
        if(auto != null){
            intent.putExtra("auto", auto)
        }
        if(parte != null){
            intent.putExtra("parte", parte)
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
        inflater.inflate(R.menu.menu_lista_parte, menu)
        //Obtener id
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionitemSeleccionadoMenu = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.editar_parte_menu -> {
                val parteSeleccionado: Parte? = obtenerPedidoSeleccionado(posicionitemSeleccionadoMenu)
                irActividad(CrearParteActivity::class.java, parte = parteSeleccionado)
                return true
            }

            R.id.eliminar_parte_menu -> {
                abrirDialogo()//
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun obtenerPedidoSeleccionado(posicionPedidoSeleccionadoMenu: Int): Parte? {
        return if(posicionPedidoSeleccionadoMenu > -1) listaPartes[posicionPedidoSeleccionadoMenu] else null
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea Eliminar esta parte?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog, which ->
                val parteSeleccionado: Parte? = obtenerPedidoSeleccionado(posicionitemSeleccionadoMenu)
                val respuesta = BaseDeDatos.dbHelper!!.eliminarParte(
                    parteSeleccionado?.idParte
                )
                if (respuesta){
                    listaPartes = BaseDeDatos.dbHelper!!.obtenerTodosLasPartesAuto(auto!!.idAuto)
                    // Actualizar el adaptador con la nueva lista de clientes
                    adapter?.clear() // Limpiar los datos antiguos del adaptador
                    adapter?.addAll(listaPartes) // Agregar los nuevos datos
                    adapter?.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
                    mostrarSnackbar("Parte eliminada!")
                }
            }
        )

        builder.setNegativeButton("Cancelar", null)
        builder.create().show()

    }

    private fun mostrarSnackbar(mensaje: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_lista_parte),
            mensaje,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}

