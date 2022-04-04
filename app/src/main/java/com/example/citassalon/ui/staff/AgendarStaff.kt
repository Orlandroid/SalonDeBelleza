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
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.util.action
import com.example.citassalon.util.displaySnack
import com.example.citassalon.util.navigate
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarStaff : Fragment(), ClickOnItem<Staff> {


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
    ): View {
        _binding = FragmentAgendarStaffBinding.inflate(layoutInflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            skeletonRecyclerView = recyclerStaff.applySkeleton(R.layout.item_staff, 4)
            toolbar.toolbarTitle.text = "Agendar Staff"
            toolbar.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            binding.buttonEtilistaAletorio.setOnClickListener {
                val estilitaAleatorio = (adaptador.getData().indices).random()
                val estilista = adaptador.getData()[estilitaAleatorio]
                navigateToAngendarService(estilista)
            }
        }
        setUpRecyclerView()
        getArgs()
        setUpObservers()
    }

    private fun getListener(): ClickOnItem<Staff> {
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
        viewModelStaff.staff.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Loading -> {
                    showSkeleton()
                }
                is ApiState.Success -> {
                    hideSkeleton()
                    if (it.data != null) {
                        setUpRecyclerView()
                        adaptador.setData(it.data)
                    }
                }
                is ApiState.Error -> {

                }
                is ApiState.NoData->{

                }
                is ApiState.ErrorNetwork -> {
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
                viewModelStaff.getSttafs()
            }
        }
    }


    private fun navigateToAngendarService(staff: Staff){
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

    override fun clikOnElement(element: Staff, position: Int?) {
        navigateToAngendarService(element)
    }


}