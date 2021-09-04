package com.example.citassalon.fragments

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
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
        binding.appCompatImageView.setOnClickListener {
            animationImage()
        }

        return binding.root
    }

    private fun animationImage(){
        binding.appCompatImageView.animate().apply {
            val valueAnimator = ValueAnimator.ofFloat(0f, 360f)

            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                // 2
                binding.appCompatImageView.rotation = value
            }

            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.duration = 2000
            valueAnimator.start()
        }
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