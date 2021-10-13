package com.example.citassalon.ui.staff


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
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
import com.example.citassalon.util.AlertsDialogMessages
import com.example.citassalon.util.ApiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarStaff : Fragment(), ClickOnStaff {


    private var _binding: FragmentAgendarStaffBinding? = null
    private val binding get() = _binding!!
    private val viewModelStaff: ViewModelStaff by viewModels()
    private val args: AgendarStaffArgs by navArgs()

    /**this is the staff what change each time what the user
     * give a clik on the recycler*/
    private var currentStaff: Staff? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarStaffBinding.inflate(layoutInflater, container, false)
        binding.recyclerStaff.setHasFixedSize(true)
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
        viewModelStaff.staffLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ApiState.Loading -> {
                    //binding.progressBarS.visibility = View.VISIBLE
                }
                is ApiState.Success -> {
                    if (it.data != null) {
                        //binding.progressBarS.visibility = View.GONE
                        binding.recyclerStaff.adapter = AdaptadorStaff(it.data, this)
                    }
                }
                is ApiState.Error -> {
                    //binding.progressBarS.visibility = View.GONE
                }
            }
        })
    }

    override fun clickOnStaff(staff: Staff) {
        binding.tvEmpleado.text = staff.nombre
        currentStaff = staff
        val acction = AgendarStaffDirections.actionAgendarStaffToAgendarServicio(
            staff,
            args.sucursal
        )
        findNavController().navigate(acction)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}