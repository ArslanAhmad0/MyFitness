package com.example.myfitness.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myfitness.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authentication:FirebaseAuth

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authentication= FirebaseAuth.getInstance()
        val currentUser=authentication.currentUser
        if(currentUser!=null){
            startActivity(Intent(this,ProfileActivity::class.java))
            finish()
        }
        onButtonClick()
    }

    private fun onButtonClick(){
        binding.LoginBTN.setOnClickListener {
          //  val loginIntent=Intent(this,MainActivity::class.java)
            if (binding.editTextTextEmailAddress.text.isEmpty()||
                binding.editTextTextPassword.text.isEmpty()){
                Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT).show()
            }else
            {
                authentication.signInWithEmailAndPassword(
                    binding.editTextTextEmailAddress.text.toString(),
                    binding.editTextTextPassword.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            startActivity(Intent(this,ProfileActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this, "Please check your email or password", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        binding.RegisterBTN.setOnClickListener {
            val registerIntent=Intent(this,RegisterActivity::class.java)
            startActivity(registerIntent)
            finish()
        }
    }
}