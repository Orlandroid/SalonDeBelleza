package com.example.citassalon.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.citassalon.databinding.FragmentHomeBinding
import com.example.citassalon.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            buttonAgendar.setOnClickListener {
                val action = HomeFragmentDirections.actionHome3ToAgendarSucursal()
                navigate(action)
            }
            btnFloatingPerfil.setOnClickListener {
                val action = HomeFragmentDirections.actionHome3ToPerfil()
                navigate(action)
            }
            btnFloatingList.setOnClickListener {
                navigate(HOME_TO_NOMBRE_ESTABLECIMEINTO)
            }
        }
        Log.i("INFO","ANDROID")
        Log.w("INFO","ANDROID")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}