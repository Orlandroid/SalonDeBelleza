package com.example.citassalon.ui.splashscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentSplashScreenBinding
import com.example.citassalon.util.navigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi(){
        launchSplash()
    }

    private fun launchSplash(){
        CoroutineScope(Dispatchers.Main).launch{
            startAnimation()
            delay(4000)
            val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToLogin()
            navigate(action)
        }
    }

    private fun startAnimation(){
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.imageView8.startAnimation(animation)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}