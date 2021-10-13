package com.example.citassalon.ui.perfil

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentPerfilBinding
import com.example.citassalon.util.AlertsDialogMessages

class Perfil : Fragment() {


    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        binding.pButtonHome.setOnClickListener {
            findNavController().navigate(R.id.action_perfil_to_home3)
        }
        binding.buttonTermAdnCondictions.setOnClickListener {
            showTermAndCondition()
        }
        return binding.root
    }

    private fun showTermAndCondition() {
        val alert = AlertsDialogMessages(requireContext())
        alert.showTermAndConditions()

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}