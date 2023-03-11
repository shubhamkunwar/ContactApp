package com.example.mycontactapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Add_Contact : AppCompatActivity() {

     lateinit var Cancel_btn : Button
     lateinit var Save_btn : Button
     lateinit var Contact_Name: EditText
     lateinit var Contact_Number: EditText
     lateinit var Contact_Home_Company: EditText
     lateinit var Contact_Title: EditText
     lateinit var Contact_Email: EditText

     //Initialising Database object
     lateinit var databaseReference: DatabaseReference

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        Cancel_btn = findViewById(R.id.cancelContactBtn)
        Save_btn = findViewById(R.id.saveContactBtn)
        Contact_Name = findViewById(R.id.Namecol)
        Contact_Number = findViewById(R.id.phone_num_col)
        Contact_Home_Company = findViewById(R.id.Home_Company_col)
        Contact_Title = findViewById(R.id.Title_col)
        Contact_Email = findViewById(R.id.Email_col)

        databaseReference= Firebase.database.reference

        Cancel_btn.setOnClickListener(){
            intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            finish()
        }
        Save_btn.setOnClickListener(){
            checkFieldvaldation()
        }

    }

    private fun checkFieldvaldation() {
        if(!TextUtils.isEmpty(Contact_Name.text.toString())){
            if(!TextUtils.isEmpty(Contact_Number.text.toString())){
                sendNewContactDetailsToFirebase()
            }else{
                Contact_Number.error = "Empty Field"
            }
        }else{
            Contact_Name.error= "Empty Field"
        }
    }

    private fun sendNewContactDetailsToFirebase() {
       var Contact_Details = hashMapOf(
           "full_Name" to Contact_Name.text.toString(),
           "Mobile_Number" to Contact_Number.text.toString(),
           "Home_Company" to Contact_Home_Company.text.toString(),
           "Title" to Contact_Title.text.toString(),
           "Email" to Contact_Email.text.toString()
       )
        databaseReference.child("Contact Details").child(Contact_Number.text.toString())
            .setValue(Contact_Details)
            .addOnCompleteListener()
            {
                if(it.isSuccessful){
                    Toast.makeText(this,"Successful", Toast.LENGTH_SHORT).show()
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    finish()
                }else{
                    Toast.makeText(this,"Error Occured", Toast.LENGTH_SHORT).show()
                }
            }
    }
}