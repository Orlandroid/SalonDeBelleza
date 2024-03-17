package com.example.citassalon.presentacion.features.staff


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarStaffBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.action
import com.example.citassalon.presentacion.features.extensions.click
import com.example.citassalon.presentacion.features.extensions.displaySnack
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.domain.entities.remote.migration.Staff
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

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = "Agendar Staff"
    )


    override fun setUpUi() {
        with(binding) {
            skeletonRecyclerView = recyclerStaff.applySkeleton(R.layout.item_staff, 8)
            binding.buttonEtilistaAletorio.click {
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
        observeApiResultGeneric(
            liveData = viewModelStaff.staff,
            onLoading = {
                showSkeleton()
            },
            onFinishLoading = {
                hideSkeleton()
            },
            hasProgressTheView = false,
            shouldCloseTheViewOnApiError = true,
            onError = { snackErrorConection() }
        ) {
            setUpRecyclerView()
        }
    }

    private fun getListener(): ClickOnItem<Staff> {
        return this
    }


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


    override fun clickOnItem(element: Staff, position: Int?) {
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