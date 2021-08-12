package com.example.citassalon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.controller.UserController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Login : Fragment() {

    private lateinit var user: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var buttonGetIn: Button
    private lateinit var buttonBack: ImageButton
    private lateinit var textForgetPassword: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        user = view.findViewById(R.id.txt_user)
        password = view.findViewById(R.id.txt_passord)
        buttonGetIn = view.findViewById(R.id.button_get_in)
        buttonGetIn.setOnClickListener {
            checkUserAndPassWord()
        }
        textForgetPassword = view.findViewById(R.id.olvidaste_contraseña)
        return view
    }

    private fun checkUserAndPassWord() {
        val userController = UserController()
        if (userController.isValidUser(user.editText?.text.toString(), password.editText?.text.toString())) {
            Toast.makeText(
                activity,
                "El usuario y la contraseña son correctos", Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_login_to_home32)
        } else {
            userController.sendSnackBar(user, "Verifica tu usuario y contraseña")
        }
    }

}