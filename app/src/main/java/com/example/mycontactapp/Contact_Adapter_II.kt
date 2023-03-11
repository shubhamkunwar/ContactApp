package com.example.mycontactapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class Contact_Adapter_II:
RecyclerView.Adapter<Contact_Adapter_II.contactViewHolder> {
    private val limit = 3
    val context: Context
    var contactList1: ArrayList<Contact_Model_II>
    interface ItemClickListener{                   //for item click
        fun itemClicked(item: Contact_Model_II)
    }
    var itemClickListener: ItemClickListener

    constructor(
        context: Context,
        contactList1: ArrayList<Contact_Model_II>,
        itemClickListener :ItemClickListener              //for item click
    ) : super() {
        this.context = context
        this.contactList1 = contactList1
        this.itemClickListener=itemClickListener
    }


    override fun onCreateViewHolder(parents: ViewGroup, viewType: Int): Contact_Adapter_II.contactViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.single_contact_layout, parents, false)
        return Contact_Adapter_II.contactViewHolder(view)
    }

    override fun onBindViewHolder(holder: contactViewHolder, position: Int) {
        holder.contactname.text = contactList1[position].full_Name
        holder.singleContactCard.setOnClickListener() {           //for item click
            itemClickListener.itemClicked(contactList1[position])
        }
    }
    override fun getItemCount(): Int {
        return contactList1.size
    }

    class contactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val contactname: TextView = itemView.findViewById(R.id.contactname)
        val singleContactCard: ConstraintLayout=itemView.findViewById(R.id.singleContactCard)
    }
    //for search bar
    fun setFilteredList(contactList1: List<Contact_Model_II>){
        contactList1.also { this.contactList1 = it as ArrayList<Contact_Model_II> }
        notifyDataSetChanged()
    }
}
