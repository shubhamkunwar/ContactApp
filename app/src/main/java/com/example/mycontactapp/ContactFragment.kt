package com.example.mycontactapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class ContactFragment : Fragment() , Contact_Adapter_II.ItemClickListener{
    lateinit var fab_add: FloatingActionButton
    lateinit var searchView: SearchView
    lateinit var recView: RecyclerView
    lateinit var singleContactList: ArrayList<Contact_Model>
    lateinit var contactList1: ArrayList<Contact_Model_II>

    lateinit var editUserName : TextView
    lateinit var editUserNum : TextView

    // lateinit var contactAdapter: Contact_Adapter
    lateinit var contactModel: Contact_Model_II
    lateinit var Contact_Adapter_II: Contact_Adapter_II
    lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_contact, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  singleContactListGenerator()

        //contactAdapter = Contact_Adapter(requireContext(), singleContactList)
        databaseReference = FirebaseDatabase.getInstance().reference.child("Contact Details")
        getContactDetailsFromFB()
        Contact_Adapter_II = Contact_Adapter_II(requireContext(), contactList1,this)
        recView = view.findViewById(R.id.recview)
        recView.layoutManager = LinearLayoutManager(requireContext())
        recView.adapter = Contact_Adapter_II
        //for floating icon
        fab_add = view.findViewById(R.id.floatingAdd)
        fab_add.setOnClickListener()
        {
            startActivity(Intent(requireContext(), Add_Contact::class.java))
        }

        //for Search
        searchView = view.findViewById(R.id.searchview)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }


    private fun getContactDetailsFromFB() {
        contactList1 = ArrayList()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (contact in snapshot.children) {
                        var contactItem: Contact_Model_II? =
                            contact.getValue(Contact_Model_II::class.java)
                        contactList1.add(contactItem!!)
                        Contact_Adapter_II.notifyDataSetChanged()
                    }
                } else {
                    Log.e("Database Empty", "No contact in DB")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<Contact_Model_II>()
            for (i in contactList1) {
                if (i.full_Name!!.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
            } else {
                Contact_Adapter_II.setFilteredList(filteredList)
            }
        }
    }

    //for item
    override fun itemClicked(

        item: Contact_Model_II
    ) {
        //pass data from Detail.kt
        val data_intent = Intent(requireContext(), Details::class.java)
        data_intent.putExtra("contactName", item.full_Name)
        data_intent.putExtra("contactNumber", item.Mobile_Number)
        startActivity(data_intent)
    }
}



   /* //for dataset of array
    private fun singleContactListGenerator() {
       singleContactList=ArrayList()
        singleContactList.add(Contact_Model(R.drawable.second,"Ranjan"))
        singleContactList.add(Contact_Model(R.drawable.second,"Patidaar"))
        singleContactList.add(Contact_Model(R.drawable.second,"Sirjii 2"))
        */

//adding dataset using for loop
    /* for (i in 0..5) {
             singleContactList.add(Contact_Model(R.drawable.second, "Co-Founder"))
         }*/





