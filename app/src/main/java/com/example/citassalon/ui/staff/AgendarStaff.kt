package com.example.citassalon.ui.staff


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.citassalon.R
import com.example.citassalon.data.models.Staff
import com.example.citassalon.databinding.FragmentAgendarStaffBinding
import com.example.citassalon.util.ApiState
import com.example.citassalon.util.action
import com.example.citassalon.util.displaySnack
import com.example.citassalon.util.navigate
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarStaff : Fragment(), ClickOnStaff {


    private var _binding: FragmentAgendarStaffBinding? = null
    private val binding get() = _binding!!
    private val viewModelStaff: ViewModelStaff by viewModels()
    private val args: AgendarStaffArgs by navArgs()
    private val adaptador = AdaptadorStaff(getListener())
    private lateinit var skeletonRecyclerView: Skeleton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarStaffBinding.inflate(layoutInflater, container, false)
        skeletonRecyclerView = binding.recyclerStaff.applySkeleton(R.layout.item_staff,4)
        setUpRecyclerView()
        getArgs()
        setUpObservers()
        return binding.root
    }

    private fun getListener(): ClickOnStaff {
        return this
    }

    private fun getArgs() {
        setValueToView(args.sucursal)
    }

    private fun setValueToView(sucursal: String) {
        binding.tvSucursal.text = sucursal
    }

    private fun setUpRecyclerView() {
        binding.recyclerStaff.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerStaff.adapter = adaptador
    }

    private fun showSkeleton() {
        skeletonRecyclerView.showSkeleton()
    }

    private fun hideSkeleton() {
        skeletonRecyclerView.showOriginal()
    }


    private fun setUpObservers() {
        viewModelStaff.staff.observe(viewLifecycleOwner, {
            when (it) {
                is ApiState.Loading -> {
                    showSkeleton()
                    binding.tvEmpleado.text = "Loding"
                }
                is ApiState.Success -> {
                    hideSkeleton()
                    if (it.data != null) {
                        binding.tvEmpleado.text = "Success"
                        setUpRecyclerView()
                        adaptador.setData(it.data)
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
        navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}