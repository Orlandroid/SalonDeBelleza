package com.example.citassalon.presentacion.ui.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentSplashScreenBinding
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.navigateAction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
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
            delay(2000)
            navigateAction(SplashScreenFragmentDirections.actionSplashScreenFragmentToLogin())
        }
    }

    private fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.imageSplash.startAnimation(animation)
    }


}