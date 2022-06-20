package com.example.projekt_zesp_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        lateinit var tv_register: TextView;
        tv_register = findViewById(R.id.btn_register)

        tv_register.setOnClickListener{
            startActivity(Intent(this@SecondActivity, MainActivity2::class.java))
        }

        lateinit var btn_login : Button;
        lateinit var et_email_login: EditText;
        lateinit var et_password_login: EditText;

        btn_login = findViewById(R.id.btn_login)
        et_email_login = findViewById(R.id.et_login_email)
        et_password_login = findViewById(R.id.et_login_password)
        btn_login.setOnClickListener{
            when {
                TextUtils.isEmpty(et_email_login.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SecondActivity,
                        "Proszę podać adres email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_password_login.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SecondActivity,
                        "Proszę wpisać hasło.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = et_email_login.text.toString().trim { it <= ' ' }
                    val password: String = et_password_login.text.toString().trim { it <= ' ' }
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this@SecondActivity,
                                        "Zostałeś poprawnie zalogowany.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this@SecondActivity, ControlActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra(
                                        "user_id",
                                        FirebaseAuth.getInstance().currentUser!!.uid
                                    )
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@SecondActivity,
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