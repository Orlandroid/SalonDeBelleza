package com.example.citassalon.ui.sucursal


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.Sucursal
import com.example.citassalon.databinding.FragmentAgendarSucursalBinding
import com.example.citassalon.ui.share_beetwen_sucursales.AdaptadorSucursal
import com.example.citassalon.ui.share_beetwen_sucursales.ClickOnSucursal
import com.example.citassalon.ui.share_beetwen_sucursales.ViewModelSucursal
import com.example.citassalon.util.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * This fragments have one viewModel wich is
 * ViewModelSucursal and is on this path
 * ui/share_beetwen_sucursales/ViewModelSucursal
 * **/

@AndroidEntryPoint
class AgendarSucursal : Fragment(), ClickOnSucursal {


    private var _binding: FragmentAgendarSucursalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelSucursal by viewModels()
    private val TAG = AgendarSucursal::class.java.simpleName

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarSucursalBinding.inflate(inflater)
        setUpUi()
        return binding.root
    }

    private fun setUpUi(){
        with(binding){
            toolbar.toolbarTitle.text="Agendar Sucursal"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        setUpObserves()
    }

    private fun setUpObserves() {
        viewModel.sucursal.observe(viewLifecycleOwner, {
            when (it) {
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.shimmerSucursal.visibility = View.GONE
                        binding.recyclerSucursal.adapter =
                            AdaptadorSucursal(it.data, this)
                    }
                }
                is ApiState.Loading -> {

                }
                is ApiState.Error -> {
                    binding.shimmerSucursal.visibility = View.GONE
                    Log.w(TAG, it.message.toString())
                }
                is ApiState.ErrorNetwork -> {
                    snackErrorConection()
                }
            }
        })
    }

    private fun snackErrorConection() {
        binding.root.displaySnack(
            getString(R.string.network_error),
            Snackbar.LENGTH_INDEFINITE
        ) {
            action(getString(R.string.retry)) {
                viewModel.getSucursales()
            }
        }
    }


    override fun clickOnSucursal(sucursal: Sucursal) {
        binding.textAgendarSucursal.text = sucursal.name
        val action = AgendarSucursalDirections.actionAgendarSucursalToAgendarStaff(
            binding.textAgendarSucursal.text.toString()
        )
        navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}