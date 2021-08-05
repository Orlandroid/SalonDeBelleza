package com.example.citassalon.controller

import com.example.citassalon.models.User

class UserController {

    private val users = listOf(User("admin@gmail.com", "admin"))

    fun checkUser(user: User): Boolean {
        users.forEach {
            if (isEmailValid(it.user)) {
                val tempUser = User(it.user, it.password)
                return tempUser == user
            }
        }
        return false
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}