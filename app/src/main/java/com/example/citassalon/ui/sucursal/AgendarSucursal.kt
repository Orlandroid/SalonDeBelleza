package com.example.citassalon.ui.sucursal


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.Sucursal
import com.example.citassalon.databinding.FragmentAgendarSucursalBinding
import com.example.citassalon.util.ApiState
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarSucursal : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener,
    ClickOnSucursal {


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
        binding.sucursalBottomNavigationView.setOnNavigationItemSelectedListener(this)
        setUpObserves()
        return binding.root
    }

    private fun setUpObserves() {
        viewModel.sucursalLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerSucursal.adapter =
                            AdaptadorSucursal(it.data, this)
                    }
                }
                is ApiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.w(TAG, it.message.toString())
                }
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_back -> {
                findNavController().navigate(R.id.action_agendarSucursal_to_home3)
                true
            }
            R.id.item_home -> {
                findNavController().navigate(R.id.action_agendarSucursal_to_home3)
                true
            }
            R.id.item_next -> {
                findNavController().navigate(R.id.action_agendarSucursal_to_agendarStaff)
                true
            }
            else -> false
        }
    }

    override fun clickOnSucursal(sucursal: Sucursal) {
        binding.textAgendarSucursal.text = sucursal.name
    }

}