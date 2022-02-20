package com.example.myfitness.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.myfitness.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var authentication: FirebaseAuth
    private var databaseReference: DatabaseReference?=null
    private var database: FirebaseDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BackDialogue.visibility=View.GONE

        authentication= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference= database?.reference!!.child("profile")

        loadProfile()

    }

    private fun loadProfile(){
        val user=authentication.currentUser
        val userReference=databaseReference!!.child(user!!.uid)

        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name =
                    "${snapshot.child("firstname").value} ${snapshot.child("lastname").value}"
                binding.NameTV.text = name
                binding.EmailTV.text = user.email
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "Something Went wrong with data", Toast.LENGTH_SHORT).show()
            }

        })
        binding.LogoutBTN.setOnClickListener {
            authentication.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }


    override fun onBackPressed() {
        binding.MainContainView.visibility=View.GONE
        binding.BackDialogue.visibility=View.VISIBLE

        binding.CancelBTN.setOnClickListener {
            binding.MainContainView.visibility=View.VISIBLE
            binding.BackDialogue.visibility=View.GONE
        }
        binding.OkBTN.setOnClickListener {
            super.onBackPressed()
        }


    }
}