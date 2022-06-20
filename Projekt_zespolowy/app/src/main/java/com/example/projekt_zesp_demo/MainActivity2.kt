package com.example.projekt_zesp_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity2 : AppCompatActivity() {

    lateinit var btn_register : Button;
    lateinit var et_email_register: EditText;
    lateinit var et_password_register: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn_register = findViewById(R.id.btn_register)
        et_email_register = findViewById(R.id.et_register_email)
        et_password_register = findViewById(R.id.et_register_password)
        lateinit var tv_login: TextView
        tv_login = findViewById(R.id.btn_login)

        tv_login.setOnClickListener{
            startActivity(Intent(this@MainActivity2, SecondActivity::class.java))
        }

        btn_register.setOnClickListener{
            when {
                TextUtils.isEmpty(et_email_register.text.toString().trim{it <= ' '}) -> {
                    Toast.makeText(
                        this@MainActivity2,
                        "Proszę podać adres email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_password_register.text.toString().trim{it <= ' '}) -> {
                    Toast.makeText(
                        this@MainActivity2,
                        "Proszę wpisać hasło.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = et_email_register.text.toString().trim{ it <= ' '}
                    val password: String = et_password_register.text.toString().trim{ it <= ' '}
                     FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                         { task ->
                             if (task.isSuccessful) {
                                 val firebaseUser: FirebaseUser = task.result!!.user!!

                                 Toast.makeText(
                                     this@MainActivity2,
                                     "Zostałeś poprawnie zarejestrowany.",
                                     Toast.LENGTH_SHORT
                                 ).show()

                                 val intent =
                                     Intent(this@MainActivity2, SecondActivity::class.java)
                                 intent.flags =
                                     Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                 intent.putExtra("user_id", firebaseUser.uid)
                                 intent.putExtra("email_id", email)
                                 startActivity(intent)
                                 finish()
                             } else {
                                 Toast.makeText(
                                     this@MainActivity2,
                                     task.exception!!.message.toString(),
                                     Toast.LENGTH_SHORT
                                 ).show()
                             }
                         })
                }
            }
        }
    }
}