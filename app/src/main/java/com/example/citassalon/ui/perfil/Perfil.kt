package com.example.citassalon.ui.perfil

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentPerfilBinding

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
        return binding.root
    }


}