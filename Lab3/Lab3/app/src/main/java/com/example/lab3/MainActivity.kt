package com.example.lab3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.ContactAdapter.OnDeleteClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : Activity() {
    private val contactList = ArrayList<Contact?>()
    private var recyclerView: RecyclerView? = null
    private var emptyText: TextView? = null
    private var adapter: ContactAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        emptyText = findViewById(R.id.emptyText)
        val addButton = findViewById<FloatingActionButton>(R.id.addButton)

        adapter = ContactAdapter(
            contactList,
            OnDeleteClickListener { position: Int -> this.removeContact(position) })
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter

        addButton.setOnClickListener { v: View? ->
            val intent = Intent(
                this,
                AddContactActivity::class.java
            )
            startActivityForResult(intent, ADD_CONTACT_REQUEST)
        }

        updateEmptyText()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK && data != null) {
            val contact = data.getSerializableExtra("contact") as Contact?
            contactList.add(contact)
            adapter!!.notifyItemInserted(contactList.size - 1)
            updateEmptyText()
        }
    }

    private fun removeContact(position: Int) {
        contactList.removeAt(position)
        adapter!!.notifyItemRemoved(position)
        updateEmptyText()
    }

    private fun updateEmptyText() {
        emptyText!!.visibility = if (contactList.isEmpty()) View.VISIBLE else View.GONE
    }

    companion object {
        private const val ADD_CONTACT_REQUEST = 1
    }
}
