package com.example.citassalon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var user: EditText
    private lateinit var password: EditText
    private lateinit var buttonGetIn: Button
    private lateinit var buttonBack: ImageButton
    private lateinit var textForgetPassword: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_nuevo)
        user = findViewById(R.id.txt_user)
        password = findViewById(R.id.txt_passord)
        buttonGetIn = findViewById(R.id.button_get_in)
        buttonBack = findViewById(R.id.button_back)
        textForgetPassword = findViewById(R.id.olvidaste_contraseña)
        textForgetPassword.setOnClickListener {
            Toast.makeText(this, "Tu contraseña es Admin", Toast.LENGTH_LONG).show()
        }
        buttonGetIn.setOnClickListener {
            checkUserAndPassWord()
        }
        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun checkUserAndPassWord() {
        if (checkPassword()) {
            if (isEmailValid(user.text.toString())) {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }
        } else {
            password.error = "Contraseña incorrecta"
        }
    }

    private fun checkPassword(): Boolean {
        val password = password.text.toString()
        return password == "Admin"
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}