package com.example.a2024swerpnd2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView





class ItemListAdapter : RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}