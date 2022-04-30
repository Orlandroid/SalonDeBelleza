package com.example.citassalon.ui.cita_agendada

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentCitaAgendadaBinding
import com.example.citassalon.main.NotificationHelper


class CitaAgendadaFragment : Fragment(), Animator.AnimatorListener {

    private var _binding: FragmentCitaAgendadaBinding? = null
    private val binding get() = _binding!!
    private var notificationHelper: NotificationHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitaAgendadaBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        notificationHelper = NotificationHelper(requireContext())
        notificationHelper!!.sendNotification(
            getString(R.string.cita_agendada),
            "Cita"
        )
        loadAnimation()
        binding.buttonAceptar.setOnClickListener {
            findNavController().popBackStack(R.id.home3, false)
        }
    }

    private fun loadAnimation() {
        binding.imageAnimation.setAnimation(R.raw.login)
        binding.imageAnimation.addAnimatorListener(getListenerAnimation())
    }

    private fun getListenerAnimation(): Animator.AnimatorListener {
        return this
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onAnimationStart(animation: Animator?) {

    }

    override fun onAnimationEnd(animitation: Animator?) {
        with(binding) {
            tvHead.text = resources.getString(R.string.muy_bien)
            tvBody.text = resources.getString(R.string.cita_agendada)
            binding.buttonAceptar.visibility = View.VISIBLE
        }
    }

    override fun onAnimationCancel(animitation: Animator?) {

    }

    override fun onAnimationRepeat(animitation: Animator?) {

    }

}