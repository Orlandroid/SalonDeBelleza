package com.example.citassalon.ui.login

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.citassalon.databinding.FragmentLoginBinding
import com.example.citassalon.util.LoginStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Login : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelLogin by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.buttonGetIn.setOnClickListener {
            checkUserAndPassWord()
        }

        binding.txtUser.addOnEditTextAttachedListener {
            animationImage()
        }
        setUpObserves()
        return binding.root
    }

    private fun animationImage() {
        binding.appCompatImageView.animate().apply {
            val valueAnimator = ValueAnimator.ofFloat(0f, 360f)

            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                binding.appCompatImageView.rotation = value
            }

            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.duration = 2000
            valueAnimator.start()
        }
    }

    private fun setUpObserves() {
        viewModel.loginStatus.observe(viewLifecycleOwner, {
            when (it) {
                is LoginStatus.LOADING -> {

                }
                is LoginStatus.SUCESS -> {
                    Toast.makeText(requireContext(), "Iniciando Session", Toast.LENGTH_SHORT).show()
                }
                is LoginStatus.ERROR -> {

                }
                is LoginStatus.NETWORKERROR -> {

                }
            }
        })
    }

    private fun checkUserAndPassWord() {
        val user = binding.txtUser.editText?.text.toString()
        val password = binding.txtPassord.editText?.text.toString()
        viewModel.login(user, password)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}