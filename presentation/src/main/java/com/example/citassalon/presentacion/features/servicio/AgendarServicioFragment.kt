package com.example.citassalon.presentacion.features.servicio


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.components.ItemStaff
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.domain.entities.remote.migration.Service
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarServicioFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    AlertDialogs.ClickOnAccept {

    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }
    private var listOfServices = arrayListOf<Service>()


    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.agendar_servicio)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ScheduleService()
            }
        }
    }

    @Composable
    fun ScheduleService() {
        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(16.dp))
            ItemStaff(
                staff = flowMainViewModel.currentStaff,
                branch = flowMainViewModel.sucursal.name
            )
            LazyColumn {
                flowMainViewModel.listOfServices.forEach { service ->
                    item {
                        TextWithArrow(
                            config = TextWithArrowConfig(
                                text = service.name,
                                clickOnItem = {
                                    navigate(AgendarServicioFragmentDirections.actionAgendarServicioToAgendarFecha())
                                }
                            )
                        )
                    }
                }
            }
        }
    }


    override fun setUpUi() {

    }
    
    override fun clickOnAccept() {
        findNavController().popBackStack()
    }

    override fun clickOnCancel() {

    }

}