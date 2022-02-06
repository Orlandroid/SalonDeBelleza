package com.example.citassalon.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.citassalon.databinding.FragmentHomeBinding
import com.example.citassalon.util.*
import java.util.concurrent.TimeUnit


class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sessionWorker =
        OneTimeWorkRequestBuilder<SessionWorker>().setInitialDelay(1L, TimeUnit.MINUTES).build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpUi()
        handleUserSession()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            buttonAgendar.setOnClickListener {
                val action = HomeDirections.actionHome3ToAgendarSucursal()
                navigate(action)
            }
            btnFloatingPerfil.setOnClickListener {
                val action = HomeDirections.actionHome3ToPerfil()
                navigate(action)
            }
            btnFloatingList.setOnClickListener {
                navigate(HOME_TO_NOMBRE_ESTABLECIMEINTO)
            }
        }
    }

    private fun handleUserSession() {
        WorkManager.getInstance(requireContext()).enqueue(sessionWorker)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}