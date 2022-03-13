package com.example.citassalon.ui.detalle_staff

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.citassalon.R
import com.example.citassalon.data.models.rickandmorty.Character
import com.example.citassalon.databinding.FragmentDetalleStaffBinding


class DetalleStaff : Fragment() {


    private var _binding: FragmentDetalleStaffBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetalleStaffArgs>()

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

    private fun setValueToView(staff: Character) {
        val options = RequestOptions().override(300,300).placeholder(R.drawable.rick)
        Glide.with(requireContext()).load(staff.image).apply(options).into(binding.image)
        binding.name.text = staff.name
        binding.tvLastLocation.text=staff.location.name
        binding.tvAlive.text=staff.status
        showColorStatus(status = staff.status)
    }

    private fun showColorStatus(status:String){
        when(status){
            "Alive" ->{
                binding.imageStatus.background.setColorFilter(Color.GREEN,PorterDuff.Mode.MULTIPLY)
            }
            "Dead" ->{
                binding.imageStatus.background.setColorFilter(Color.RED,PorterDuff.Mode.MULTIPLY)
            }
            "unknown" ->{
                binding.imageStatus.background.setColorFilter(Color.DKGRAY,PorterDuff.Mode.MULTIPLY)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}