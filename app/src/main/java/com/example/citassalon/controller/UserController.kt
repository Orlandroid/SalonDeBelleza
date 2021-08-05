package com.example.citassalon.controller

import android.view.View
import android.widget.EditText
import com.example.citassalon.models.User
import com.google.android.material.snackbar.Snackbar

class UserController {

    private val users = listOf(User("admin@gmail.com", "admin"))

    private fun checkUser(user: User): Boolean {
        users.forEach {
            if (isEmailValid(it.user)) {
                val tempUser = User(it.user, it.password)
                return tempUser == user
            }
        }
        return false
    }


    fun isValidUser(userP: String, passwordP: String): Boolean {
        val usuario = User(userP, passwordP)
        return checkUser(usuario)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun sendSnackBar(view: View, mensaje: String) {
        Snackbar.make(view, mensaje, Snackbar.LENGTH_SHORT).show()
    }

}