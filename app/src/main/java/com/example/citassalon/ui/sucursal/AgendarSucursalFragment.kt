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
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.databinding.FragmentAgendarSucursalBinding
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.ui.share_beetwen_sucursales.AdaptadorSucursal
import com.example.citassalon.ui.share_beetwen_sucursales.ViewModelSucursal
import com.example.citassalon.util.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarSucursalFragment : Fragment(), ClickOnItem<Sucursal> {


    private var _binding: FragmentAgendarSucursalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelSucursal by viewModels()
    private val TAG = AgendarSucursalFragment::class.java.simpleName

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgendarSucursalBinding.inflate(inflater)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            toolbar.toolbarTitle.text = "Agendar Sucursal"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        setUpObserves()
    }

    private fun getListener(): ClickOnItem<Sucursal> = this

    private fun setUpObserves() {
        viewModel.sucursal.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Success -> {
                    if (it.data != null) {
                        with(binding) {
                            shimmerSucursal.visibility = View.GONE
                            imageAnimation.visibility = View.GONE
                            recyclerSucursal.adapter =
                                AdaptadorSucursal(it.data, getListener())
                        }
                    }
                }
                is ApiState.Loading -> {
                    with(binding){
                        shimmerSucursal.visibility = View.VISIBLE
                        recyclerSucursal.visibility=View.VISIBLE
                        imageAnimation.visibility=View.GONE
                    }
                }
                is ApiState.NoData -> {
                    with(binding) {
                        imageAnimation.setAnimation(getRandomNoDataAnimation())
                        imageAnimation.visibility = View.VISIBLE
                    }
                }
                is ApiState.Error -> {
                    with(binding) {
                        shimmerSucursal.visibility = View.GONE
                    }
                }
                is ApiState.ErrorNetwork -> {
                    with(binding) {
                        shimmerSucursal.visibility = View.GONE
                        recyclerSucursal.visibility=View.GONE
                        imageAnimation.visibility = View.VISIBLE
                        imageAnimation.setAnimation(getRandomErrorNetworkAnimation())
                    }
                    snackErrorConection()
                }
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun clikOnElement(element: Sucursal, position: Int?) {
        binding.textAgendarSucursal.text = element.name
        val action = AgendarSucursalFragmentDirections.actionAgendarSucursalToAgendarStaff(
            binding.textAgendarSucursal.text.toString()
        )
        navigate(action)
    }

}