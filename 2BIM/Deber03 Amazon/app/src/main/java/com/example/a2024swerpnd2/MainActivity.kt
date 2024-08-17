package com.example.a2024swerpnd2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var mainRecyclerView1: RecyclerView
    private lateinit var recycler2: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Encuentra los RecyclerView en el layout
        mainRecyclerView1 = findViewById(R.id.main_recycler_view)
        recycler2 = findViewById(R.id.main_recycler)

        // Configura el primer RecyclerView
        mainRecyclerView1.apply {
            adapter = ItemListAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // Configura el segundo RecyclerView
        recycler2.apply {
            adapter = ItemListAdapter2()
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}
