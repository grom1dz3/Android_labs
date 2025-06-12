package com.example.lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ContactAdapter(
    private val contacts: ArrayList<Contact?>,
    private val deleteClickListener: OnDeleteClickListener?
) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    interface OnDeleteClickListener {
        fun onDeleteClick(position: Int)
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photo: ImageView = itemView.findViewById<ImageView>(R.id.contactPhoto)
        var name: TextView = itemView.findViewById<TextView>(R.id.contactName)
        var email: TextView = itemView.findViewById<TextView>(R.id.contactEmail)
        var phone: TextView = itemView.findViewById<TextView>(R.id.contactPhone)
        var deleteBtn: Button = itemView.findViewById<Button>(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.name.text = contact.name
        holder.email.text = contact.email
        holder.phone.text = contact.phone
        holder.photo.setImageBitmap(contact.photo)

        holder.deleteBtn.setOnClickListener { v: View? ->
            deleteClickListener?.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}
