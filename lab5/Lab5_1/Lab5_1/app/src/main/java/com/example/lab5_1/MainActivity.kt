package com.example.lab5_1

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // Кількість елементів у сітці
    private val itemCount = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Пошук RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Налаштування сітки з 4 колонками
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        // Створення списку випадкових чисел
        val items = List(itemCount) { (1..99).random() }

        // Встановлення адаптера
        recyclerView.adapter = ItemAdapter(items) { value ->
            // Показ діалогу при натисканні на елемент
            AlertDialog.Builder(this)
                .setTitle("Обране число")
                .setMessage("Ви обрали число: $value")
                .setPositiveButton("OK", null)
                .show()
        }
    }
}
