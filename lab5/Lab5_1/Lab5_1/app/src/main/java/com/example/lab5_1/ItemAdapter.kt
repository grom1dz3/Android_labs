package com.example.lab5_1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
    private val items: List<Int>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemText: TextView = itemView.findViewById(R.id.item_text)
        val container: View = itemView.findViewById(R.id.item_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val value = items[position]
        holder.itemText.text = value.toString()

        // Генерація випадкового кольору
        val color = Color.rgb((0..255).random(), (0..255).random(), (0..255).random())
        holder.container.setBackgroundColor(color)

        // Обробка натиснення
        holder.container.setOnClickListener {
            onItemClick(value)
        }
    }
}
