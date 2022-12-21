package com.example.citassalon.presentacion.ui.staff


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.migration.Staff
import com.example.citassalon.domain.state.ApiState
import com.example.citassalon.databinding.FragmentAgendarStaffBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.main.AlertDialogs.Companion.ERROR_MESSAGE
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.action
import com.example.citassalon.presentacion.ui.extensions.displaySnack
import com.example.citassalon.presentacion.ui.extensions.navigate
import com.example.citassalon.presentacion.ui.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.util.ERROR_SERVIDOR
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarStaffFragment :
    BaseFragment<FragmentAgendarStaffBinding>(R.layout.fragment_agendar_staff), ClickOnItem<Staff>,
    AlertDialogs.ClickOnAccept {


    private val viewModelStaff: StaffViewModel by viewModels()
    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }
    private val adaptador = StaffAdapter(getListener())
    private lateinit var skeletonRecyclerView: Skeleton


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() {
        with(binding) {
            skeletonRecyclerView = recyclerStaff.applySkeleton(R.layout.item_staff, 8)
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
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModelStaff.staff.observe(viewLifecycleOwner) {
            if (it is ApiState.Loading) {
                showSkeleton()
            } else {
                hideSkeleton()
            }
            when (it) {
                is ApiState.Success -> {
                    if (it.data != null) {
                        setUpRecyclerView()
                    }
                }
                is ApiState.Error -> {
                    val alert = AlertDialogs(
                        messageBody = ERROR_SERVIDOR,
                        kindOfMessage = ERROR_MESSAGE,
                        clickOnAccept = getListenerDialog()
                    )
                    activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
                }
                is ApiState.NoData -> {

                }
                is ApiState.ErrorNetwork -> {
                    snackErrorConection()
                }
                else -> {}
            }
        }
    }

    private fun getListener(): ClickOnItem<Staff> {
        return this
    }

    private fun getListenerDialog(): AlertDialogs.ClickOnAccept = this

    private fun getArgs() {
        setValueToView(flowMainViewModel.sucursal.name)
    }

    private fun setValueToView(sucursal: String) {
        binding.tvSucursal.text = sucursal
    }

    private fun setUpRecyclerView() {
        binding.recyclerStaff.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerStaff.adapter = adaptador
        adaptador.setData(flowMainViewModel.listOfStaffs)
    }

    private fun showSkeleton() {
        skeletonRecyclerView.showSkeleton()
    }

    private fun hideSkeleton() {
        skeletonRecyclerView.showOriginal()
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


    private fun navigateToAngendarService(staff: Staff) {
        binding.tvEmpleado.text = staff.nombre
        val action = AgendarStaffFragmentDirections.actionAgendarStaffToAgendarServicio()
        navigate(action)
    }


    override fun clikOnElement(element: Staff, position: Int?) {
        flowMainViewModel.currentStaff = element
        if (position == 0) {
            val action =
                AgendarStaffFragmentDirections.actionAgendarStaffToDetalleStaff()
            navigate(action)
            return
        }
        navigateToAngendarService(element)
    }

    override fun clickOnAccept() {
        findNavController().popBackStack()
    }

    override fun clickOnCancel() {

    }


}