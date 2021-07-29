package com.example.citassalon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
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

    val activitys = arrayOf(
        AgendarConfirmacion::class.java,
        AgendarFecha::class.java,
        AgendarHora::class.java,
        AgendarServicio::class.java,
        AgendarStaff::class.java,
        AgendarSucursal::class.java,
        Home::class.java,
        MainActivity::class.java
    )

    private fun checkUserAndPassWord() {
        if (checkPassword()) {
            if (isEmailValid(user.text.toString())) {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            } else {
                sendSnackBar(user, "Ingresa una cuenta de correo electronico correcta")
            }
        } else {
            password.error = "Contraseña incorrecta"
        }
    }

    private fun checkPassword(): Boolean {
        val password = password.text.toString()
        return password == "Admin"
    }

    private fun sendSnackBar(view: View, mensaje: String) {
        Snackbar.make(view, mensaje, Snackbar.LENGTH_SHORT).show()
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}