package com.example.myfitness.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myfitness.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.runBlocking

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authentication: FirebaseAuth
    private var databaseReference: DatabaseReference?=null
    private var database: FirebaseDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authentication= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference= database?.reference!!.child("profile")
        binding.RegisterBTN.setOnClickListener {
            runBlocking { registrationClick() }
        }

    }

    private fun registrationClick(){


            if(binding.EmailAddressET.text.isEmpty()|| binding.PasswordET.text.isEmpty())
            {
                Toast.makeText(this, "Please Enter Email and Password ", Toast.LENGTH_SHORT).show()
            }
            else {
                authentication.createUserWithEmailAndPassword(
                    binding.EmailAddressET.text.toString(),
                    binding.PasswordET.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val currentUser = authentication.currentUser
                        val currentUserDb = databaseReference!!.child(currentUser!!.uid)
                        currentUserDb.child("firstname")
                            .setValue(binding.FirstNameET.text.toString())
                        currentUserDb.child("lastname").setValue(binding.LastNameET.text.toString())

                        Toast.makeText(this, "Registration Done", Toast.LENGTH_SHORT).show()

                        finish()
                        startActivity(Intent(this,ProfileActivity::class.java))
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }

                }

            }

    }

    override fun onBackPressed() {
        startActivity(Intent(this,LoginActivity::class.java))
        super.onBackPressed()
    }

}