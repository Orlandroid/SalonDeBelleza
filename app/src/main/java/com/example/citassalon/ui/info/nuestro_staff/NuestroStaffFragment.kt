package com.example.citassalon.ui.info.nuestro_staff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.citassalon.databinding.FragmentNuestroStaffBinding


class NuestroStaffFragment : Fragment() {

    private var _binding: FragmentNuestroStaffBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuestroStaffBinding.inflate(layoutInflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi(){
        with(binding){
            toolbarLayout.toolbarTitle.text="Nuestro Staff"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}