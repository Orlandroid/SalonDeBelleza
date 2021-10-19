package com.example.citassalon.ui.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.citassalon.databinding.SignInBinding
import com.example.citassalon.util.SessionStatus
import com.example.citassalon.util.showSnack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUp : Fragment() {

    private var _binding: SignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelSignUp by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignInBinding.inflate(layoutInflater, container, false)
        setUpObservers()
        binding.buttonRegistarse.setOnClickListener {
            singUp()
        }
        return binding.root
    }

    private fun setUpObservers() {
        viewModel.singUp.observe(viewLifecycleOwner, {
            when (it) {
                is SessionStatus.LOADING -> {

                }
                is SessionStatus.SUCESS -> {
                    binding.root.showSnack("Usuario registraro correctamente")
                }
                is SessionStatus.ERROR -> {
                    binding.root.showSnack("Error al registar al usuario")
                }
                is SessionStatus.NETWORKERROR -> {
                    binding.root.showSnack("Error de red verifica que tengas conexion a internet")
                }
            }
        })
    }


    private fun singUp() {
        val user = binding.user.editText?.text.toString().trim()
        val password = binding.password.editText?.text.toString().trim()
        if (user.isNotEmpty() && password.isNotEmpty()) {
            viewModel.sinUp(user, password)
        } else {
            Toast.makeText(requireContext(), "Debes de ingresar ambos campos", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}