package com.example.citassalon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.citassalon.controller.UserController
import com.example.citassalon.models.User
import com.google.android.material.snackbar.Snackbar

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
            sendSnackBar(it, "Tu contraseña es Admin")
        }
        buttonGetIn.setOnClickListener {
            checkUserAndPassWord()
        }
        buttonBack.setOnClickListener {
            finish()
        }
    }


    private fun isValidUser(): Boolean {
        val user = user.text.toString()
        val password = password.text.toString()
        val usuario = User(user, password)
        val userController = UserController()
        return userController.checkUser(usuario)
    }

    private fun checkUserAndPassWord() {
        if (isValidUser()) {
            Toast.makeText(
                this,
                "El usuario y la contraseña son correctos", Toast.LENGTH_SHORT
            ).show()
        } else {
            sendSnackBar(user, "Verifica tu usuario y contraseña")
        }
    }


    private fun sendSnackBar(view: View, mensaje: String) {
        Snackbar.make(view, mensaje, Snackbar.LENGTH_SHORT).show()
    }


}