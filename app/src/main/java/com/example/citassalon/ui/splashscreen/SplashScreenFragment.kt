package com.example.citassalon.ui.splashscreen

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentSplashScreenBinding
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.navigate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding>(R.layout.fragment_splash_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun setUpUi() {
        launchSplash()
    }

    private fun launchSplash() {
        lifecycleScope.launch {
            startAnimation()
            delay(4000)
            val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToLogin()
            navigate(action)
        }
    }

    private fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.imageSplash.startAnimation(animation)
    }


}