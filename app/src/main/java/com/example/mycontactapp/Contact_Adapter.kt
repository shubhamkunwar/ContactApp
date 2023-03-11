package com.example.mycontactapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import org.intellij.lang.annotations.Language

class Contact_Adapter(val context: Context, var singleContactList: ArrayList<Contact_Model>) :
RecyclerView.Adapter<Contact_Adapter.contactViewHolder>(){


    override fun onCreateViewHolder(parents: ViewGroup, viewType:Int):contactViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.single_contact_layout, parents,false)
        return contactViewHolder(view)
    }


    override fun onBindViewHolder(holder: contactViewHolder, position: Int){
        holder.contactName.text=singleContactList[position].contactName
        holder.profileImage.setImageResource(singleContactList[position].profilePhoto)
    }


    override fun getItemCount(): Int {
        return singleContactList.size
    }


    class contactViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val profileImage:ShapeableImageView=itemView.findViewById(R.id.ProfileImage)
        val contactName: TextView=itemView.findViewById(R.id.contactname)
    }

   /* //for search bar
    fun setFilteredList(singleContactList: List<Contact_Model>){
        singleContactList.also { this.singleContactList = it as ArrayList<Contact_Model> }
        notifyDataSetChanged()
    }*/
}


