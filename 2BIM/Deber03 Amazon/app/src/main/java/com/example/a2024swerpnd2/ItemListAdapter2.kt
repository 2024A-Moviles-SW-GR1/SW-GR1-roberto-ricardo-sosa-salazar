package com.example.a2024swerpnd2


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemListAdapter2 : RecyclerView.Adapter<ItemListAdapter2.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout2, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}