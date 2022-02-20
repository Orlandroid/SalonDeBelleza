package com.example.citassalon.ui.info.sucursales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.data.models.Sucursal
import com.example.citassalon.databinding.FragmentSucursalesBinding
import com.example.citassalon.ui.share_beetwen_sucursales.AdaptadorSucursal
import com.example.citassalon.ui.share_beetwen_sucursales.ClickOnSucursal
import com.example.citassalon.ui.share_beetwen_sucursales.ViewModelSucursal
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.util.navigate
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
    ): View {
        _binding = FragmentSucursalesBinding.inflate(layoutInflater, container, false)
        setUpUi()
        setUpObserves()
        return binding.root
    }


    private fun setUpUi(){
        with(binding){
            toolbarLayout.toolbarTitle.text = "Sucursales"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpObserves() {
        viewModel.sucursal.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.shimmerSucursal.visibility = View.GONE
                        binding.recyclerView.adapter =
                            AdaptadorSucursal(it.data, getListener())
                    }
                }
                is ApiState.Loading -> {

                }
                is ApiState.Error -> {

                }
                is ApiState.ErrorNetwork -> {

                }
            }
        }
    }

    private fun getListener(): ClickOnSucursal = this

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun clickOnSucursal(sucursal: Sucursal) {
        val action = SucursalesDirections.actionSucursales2ToNegocioInfo()
        navigate(action)
    }

}