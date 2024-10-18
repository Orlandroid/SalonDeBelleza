package com.example.citassalon.presentacion.features.schedule_appointment.cita_agendada

import android.animation.Animator
import android.view.View
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentCitaAgendadaBinding
import com.example.citassalon.presentacion.main.NotificationHelper
import com.example.citassalon.presentacion.features.base.BaseFragment


class CitaAgendadaFragment :
    BaseFragment<FragmentCitaAgendadaBinding>(R.layout.fragment_cita_agendada),
    Animator.AnimatorListener {

    private var notificationHelper: NotificationHelper? = null
//    private val args: CitaAgendadaFragmentArgs by navArgs()


    override fun setUpUi() {
//        val arg = HistorialDetailFragmentArgs(args.apponitment).toBundle()
//        val pendingIntent = findNavController()
//            .createDeepLink()
//            .setDestination(R.id.historialDetailFragment)
//            .setArguments(arg)
//            .createPendingIntent()


        notificationHelper = NotificationHelper(requireContext())
//        notificationHelper!!.sendNotification(
//            getString(R.string.cita_agendada),
//            "Cita",
//            pendingIntent
//        )
        loadAnimation()
        binding.buttonAceptar.setOnClickListener {
//            findNavController().popBackStack(R.id.home3, false)
        }
    }

    private fun loadAnimation() {
        binding.imageAnimation.setAnimation(R.raw.login)
        binding.imageAnimation.addAnimatorListener(getListenerAnimation())
    }

    private fun getListenerAnimation(): Animator.AnimatorListener {
        return this
    }


    override fun onAnimationStart(p0: Animator) {

    }

    override fun onAnimationEnd(p0: Animator) {
        with(binding) {
            tvHead.text = resources.getString(R.string.muy_bien)
            tvBody.text = resources.getString(R.string.cita_agendada)
            binding.buttonAceptar.visibility = View.VISIBLE
        }
    }

    override fun onAnimationCancel(p0: Animator) {

    }

    override fun onAnimationRepeat(p0: Animator) {

    }

}