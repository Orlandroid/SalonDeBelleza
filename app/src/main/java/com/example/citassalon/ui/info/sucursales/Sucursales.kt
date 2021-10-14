package com.example.citassalon.ui.info.sucursales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.citassalon.data.models.Sucursal
import com.example.citassalon.databinding.FragmentSucursalesBinding
import com.example.citassalon.ui.share_beetwen_sucursales.AdaptadorSucursal
import com.example.citassalon.ui.share_beetwen_sucursales.ClickOnSucursal
import com.example.citassalon.ui.share_beetwen_sucursales.ViewModelSucursal
import com.example.citassalon.util.ApiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Sucursales : Fragment(), ClickOnSucursal {

    private var _binding: FragmentSucursalesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelSucursal by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSucursalesBinding.inflate(layoutInflater, container, false)
        setUpObserves()
        return binding.root
    }

    private fun setUpObserves() {
        viewModel.sucursal.observe(viewLifecycleOwner, {
            when (it) {
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.recyclerView.adapter =
                            AdaptadorSucursal(it.data, this)
                    }
                }
                is ApiState.Loading -> {

                }
                is ApiState.Error -> {

                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun clickOnSucursal(sucursal: Sucursal) {
        Toast.makeText(requireContext(), "Click en ${sucursal.name}", Toast.LENGTH_SHORT).show()
    }
}