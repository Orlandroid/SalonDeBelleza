package com.example.citassalon.ui.staff


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.citassalon.R
import com.example.citassalon.data.models.Staff
import com.example.citassalon.databinding.FragmentAgendarStaffBinding
import com.example.citassalon.util.ApiState
import com.example.citassalon.util.action
import com.example.citassalon.util.displaySnack
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarStaff : Fragment(), ClickOnStaff {


    private var _binding: FragmentAgendarStaffBinding? = null
    private val binding get() = _binding!!
    private val viewModelStaff: ViewModelStaff by viewModels()
    private val args: AgendarStaffArgs by navArgs()

    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarStaffBinding.inflate(layoutInflater, container, false)
        binding.recyclerStaff.layoutManager = GridLayoutManager(requireContext(), 2)
        setUpObservers()
        getArgs()
        return binding.root
    }

    private fun getArgs() {
        setValueToView(args.sucursal)
    }

    private fun setValueToView(sucursal: String) {
        binding.tvSucursal.text = sucursal
    }


    private fun setUpObservers() {
        viewModelStaff.staff.observe(viewLifecycleOwner, {
            when (it) {
                is ApiState.Loading -> {

                }
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.recyclerStaff.adapter = AdaptadorStaff(it.data, this)
                    }
                }
                is ApiState.Error -> {

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
                viewModelStaff.getSttafs()
            }
        }
    }

    override fun clickOnStaff(staff: Staff) {
        binding.tvEmpleado.text = staff.nombre
        val action = AgendarStaffDirections.actionAgendarStaffToAgendarServicio(
            staff,
            args.sucursal
        )
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}