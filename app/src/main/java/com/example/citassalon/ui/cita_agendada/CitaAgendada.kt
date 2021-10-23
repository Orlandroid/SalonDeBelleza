package com.example.citassalon.ui.cita_agendada

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentCitaAgendadaBinding
import com.example.citassalon.util.CITA_AGENDADA_TO_HOME
import com.example.citassalon.util.navigate


class CitaAgendada : Fragment() {

    private var _binding: FragmentCitaAgendadaBinding? = null
    private val binding get() = _binding!!
    private val CHANNEL_ID = "hola"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCitaAgendadaBinding.inflate(inflater, container, false)
        binding.buttonAceptar.setOnClickListener {
            it.navigate(CITA_AGENDADA_TO_HOME)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
            simpleNotification()
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannel() {
        val name = getString(R.string.Descuento)
        val descriptionText = getString(R.string.date_selected)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun simpleNotification() {
        var builder = context?.let {
            NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.watch_later_24px) //seteamos el ícono de la push notification
                .setColor(it.getColor(R.color.beige)) //definimos el color del ícono y el título de la notificación
                .setContentTitle(getString(R.string.cita_agendada)) //seteamos el título de la notificación
                .setContentText(getString(R.string.notificacion_mensaje)) //seteamos el cuerpo de la notificación
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        } //Ponemos una prioridad por defecto

        //lanzamos la notificación
        with(NotificationManagerCompat.from(requireContext())) {
            builder?.let { notify(20, it.build()) } //en este caso pusimos un id genérico
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}