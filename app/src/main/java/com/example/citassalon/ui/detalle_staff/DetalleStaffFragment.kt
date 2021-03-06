package com.example.citassalon.ui.detalle_staff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.data.models.Staff
import com.example.citassalon.databinding.FragmentDetalleStaffBinding


class DetalleStaffFragment : Fragment() {


    private var _binding: FragmentDetalleStaffBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetalleStaffFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleStaffBinding.inflate(layoutInflater)
        setUpUi()
        return binding.root
    }

    private fun setUpUi(){
        with(binding){
            toolbar.toolbarTitle.text="Detalle Staff"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        getFromArgs()
    }

    private fun getFromArgs() {
        setValueToView(args.currentStaff)
    }

    private fun setValueToView(staff: Staff) {
        binding.image.setImageResource(staff.getResourceImage())
        binding.name.text = staff.nombre
        binding.ratingBarEvaluation.rating = staff.valoracion
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}