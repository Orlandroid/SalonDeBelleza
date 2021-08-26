package com.example.citassalon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.controller.UserController
import com.example.citassalon.databinding.FragmentLoginBinding

class Login : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.buttonGetIn.setOnClickListener {
            checkUserAndPassWord()
        }
        return binding.root
    }

    private fun checkUserAndPassWord() {
        val userController = UserController()
        if (userController.isValidUser(
                binding.txtUser.editText?.text.toString(),
                binding.txtPassord.editText?.text.toString()
            )
        ) {
            Toast.makeText(
                activity,
                "El usuario y la contraseña son correctos", Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_login_to_home32)
        } else {
            userController.sendSnackBar(binding.txtUser, "Verifica tu usuario y contraseña")
        }
    }

}