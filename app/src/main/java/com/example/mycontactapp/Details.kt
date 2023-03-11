package com.example.mycontactapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Details : AppCompatActivity() {
    lateinit var Details_user_name : TextView
    lateinit var Details_user_num : TextView
    lateinit var Details_msg_icon : ShapeableImageView

    //for edit details
    lateinit var Details_edit : TextView
    lateinit var editBottomSheetDialog: BottomSheetDialog
    lateinit var edit_Cancel_btn : Button
    lateinit var edit_Save_btn : Button
    lateinit var edit_Contact_Name: EditText
    lateinit var edit_Contact_Number: EditText
    lateinit var edit_Contact_Home_Company: EditText
    lateinit var edit_Contact_Title: EditText
    lateinit var edit_Contact_Email: EditText
    // end of  edit details

    lateinit var Details_call_icon : ShapeableImageView
    lateinit var databaseRefrence : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        databaseRefrence = Firebase.database.reference

        // taking data from contactFragnment.kt  ......start
        Details_user_name = findViewById(R.id.NametextView)
        Details_user_num = findViewById(R.id.phone_num_details)
        Details_user_name.text = intent.getStringExtra("contactName").toString()
        Details_user_num.text =intent.getStringExtra("contactNumber").toString()
         // end





        //calling icon for details
        Details_call_icon = findViewById(R.id.callButtonDetails)
        Details_call_icon.setOnClickListener(){

           val callintent = Intent(Intent.ACTION_CALL)
            callintent.setData(Uri.parse("tel:"+Details_user_num.text.toString()))
            startActivity(callintent)
            //end

           // for Edit number

        }


        Details_edit = findViewById(R.id.edit_textView2)
        Details_edit.setOnClickListener(){
            Log.d("Details","Executed")
            showDetailsEditDialog()
        }
        Details_msg_icon = findViewById(R.id.msgButtonDetails)
        Details_msg_icon.setOnClickListener(){
            val  smsintent = Intent(Intent.ACTION_VIEW)
            smsintent.putExtra("sms_body","default contents")
            smsintent.setType("vnd.android-dir/mms-sms")
            smsintent.setData(Uri.parse("sms:"+Details_user_num.text.toString()))
            startActivity(smsintent)
        }
    }

    private fun showDetailsEditDialog() {

            val view: View = layoutInflater.inflate(R.layout.details_edit_bottom_sheet, null)
            editBottomSheetDialog = BottomSheetDialog(this)

            editBottomSheetDialog.setContentView(view)
            editBottomSheetDialog.show()
            editBottomSheetDialog.setCancelable(false)
            edit_Contact_Name = view.findViewById(R.id.Edit_Namecol)
            edit_Contact_Number = view.findViewById(R.id.Edit_phone_num_col)
            edit_Contact_Home_Company = view.findViewById(R.id.Edit_Home_Company_col)
            edit_Contact_Title = view.findViewById(R.id.Edit_Title_col)
            edit_Contact_Email = view.findViewById(R.id.Edit_Email_col)
            edit_Save_btn = view.findViewById(R.id.Edit_saveContactBtn)
        edit_Cancel_btn = view.findViewById(R.id.Edit_cancelContactBtn)
        edit_Cancel_btn.setOnClickListener(){
            editBottomSheetDialog.dismiss()
        }

        edit_Save_btn.setOnClickListener()
        {
            sendNewContactDetailsToFirebase()
        }

            getContactDetailsFromFB()
            edit_Cancel_btn.setOnClickListener()
            {
                editBottomSheetDialog.dismiss()
            }
            edit_Contact_Name.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    //TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    checkFieldValidation()
                }

                override fun afterTextChanged(s: Editable?) {
                    //TODO("Not yet implemented")
                }


            })
            edit_Contact_Number.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    //TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    checkFieldValidation()
                }

                override fun afterTextChanged(s: Editable?) {
                    //TODO("Not yet implemented")
                }
            })
            edit_Contact_Home_Company.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    //TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    checkFieldValidation()
                }

                override fun afterTextChanged(s: Editable?) {
                    //TODO("Not yet implemented")
                }
            })
            edit_Contact_Title.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    //TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    checkFieldValidation()
                }

                override fun afterTextChanged(s: Editable?) {
                    //TODO("Not yet implemented")
                }
            })
            edit_Contact_Title.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    //TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    checkFieldValidation()
                }

                override fun afterTextChanged(s: Editable?) {
                    //TODO("Not yet implemented")
                }
            })
        }


    fun getContactDetailsFromFB() {
        databaseRefrence.child("Contact Details")
            .child(intent.getStringExtra("contactNumber").toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    edit_Contact_Name.setText(snapshot.child("full_Name").value.toString())
                    edit_Contact_Number.setText(snapshot.child("Mobile_Number").value.toString())
                    edit_Contact_Home_Company.setText(snapshot.child("Home_Company").value.toString())
                    edit_Contact_Title.setText(snapshot.child("Title").value.toString())
                    edit_Contact_Email.setText(snapshot.child("Email").value.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun checkFieldValidation() {

        if (!TextUtils.isEmpty(edit_Contact_Number.text.toString())) {
            if (!TextUtils.isEmpty(edit_Contact_Name.text.toString())) {
            } else {
                edit_Contact_Name.error = "Empty Field"
            }
        } else {
            edit_Contact_Number.error = "Empty Field"
        }
    }

    private fun sendNewContactDetailsToFirebase() {
        var Contact_Details = hashMapOf(
            "full_Name" to edit_Contact_Name.text.toString(),
            "Mobile_Number" to edit_Contact_Number.text.toString(),
            "Home_Company" to edit_Contact_Home_Company.text.toString(),
            "Title" to  edit_Contact_Title.text.toString(),
            "Email" to  edit_Contact_Email.text.toString()
        )
        databaseRefrence.child("Contact Details").child(edit_Contact_Number.text.toString())
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
