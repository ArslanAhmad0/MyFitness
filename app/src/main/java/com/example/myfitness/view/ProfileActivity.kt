package com.example.myfitness.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myfitness.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var authentication: FirebaseAuth
    private var databaseReference: DatabaseReference?=null
    private var database: FirebaseDatabase?=null

    // for notification
    private val channelId="channel id"
    private val notificationId=101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BackDialogue.visibility=View.GONE

        authentication= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference= database?.reference!!.child("profile")

        loadProfile()
        createNotificationChannel()


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
                sendNotification()
                Toast.makeText(this@ProfileActivity, user.uid, Toast.LENGTH_SHORT).show()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "Something Went wrong with data", Toast.LENGTH_SHORT).show()
            }

        })
        binding.LogoutBTN.setOnClickListener {
            binding.MainContainView.visibility=View.GONE
            binding.BackDialogue.visibility=View.VISIBLE
            "Logout".also { binding.OkBTN.text = it }
            binding.OkBTN.setOnClickListener {
                authentication.signOut()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
            binding.CancelBTN.setOnClickListener {
                binding.MainContainView.visibility=View.VISIBLE
                binding.BackDialogue.visibility=View.GONE
            }


        }
    }


    override fun onBackPressed() {
        binding.MainContainView.visibility=View.GONE
        binding.BackDialogue.visibility=View.VISIBLE
        "Exit".also { binding.OkBTN.text=it }

        binding.CancelBTN.setOnClickListener {
            binding.MainContainView.visibility=View.VISIBLE
            binding.BackDialogue.visibility=View.GONE
        }
        binding.OkBTN.setOnClickListener {
            super.onBackPressed()
        }

    }

    private fun createNotificationChannel(){

            val name = "notification title"
            val descriptionText = "Notification description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId,name,importance).apply {
               description = descriptionText
            }
            val notificationManager:NotificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

    }
    private fun sendNotification(){
        val builder=NotificationCompat.Builder(this,channelId)
            .setSmallIcon(android.R.drawable.btn_star_big_on)
            .setContentTitle("LogIn")
            .setContentText("You are now login to MyFitness Successfully")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)){
            notify(notificationId,builder.build())

        }
    }
}