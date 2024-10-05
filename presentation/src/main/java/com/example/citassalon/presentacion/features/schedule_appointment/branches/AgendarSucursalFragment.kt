package com.example.citassalon.presentacion.features.schedule_appointment.branches


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.components.TextWithArrow
import com.example.citassalon.presentacion.features.components.TextWithArrowConfig
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.extensions.GenericResultState
import com.example.citassalon.presentacion.features.share_beetwen_sucursales.BranchViewModel
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.domain.entities.remote.migration.NegoInfo
import com.example.domain.state.ApiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AgendarSucursalFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    ClickOnItem<NegoInfo>, AlertDialogs.ClickOnAccept {

    private val branchViewModel: BranchViewModel by viewModels()
//    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
//        defaultViewModelProviderFactory
//    }

//    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
//        showToolbar = false, toolbarTitle = getString(R.string.agendar_sucursal)
//    )

    override fun setUpUi() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ScheduleBranch(branchViewModel)
            }
        }
    }

    @Composable
    fun ScheduleBranch(branchViewModel: BranchViewModel) {
        BaseComposeScreen(
            navController = rememberNavController(), toolbarConfiguration = ToolbarConfiguration(
                title = stringResource(
                    id = R.string.agendar_sucursal
                )
            )
        ) {
            val branches = branchViewModel.branches.observeAsState()
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ShowBranches(branches = branches)
            }
        }
    }

    //TODO add shimmer effect to list
    @Composable
    private fun ShowBranches(branches: State<ApiState<List<NegoInfo>>?>) {
        GenericResultState(state = branches) {
            LazyColumn {
                branches.value?.data?.forEach { branch ->
                    item {
                        TextWithArrow(
                            TextWithArrowConfig(text = branch.sucursal.name,
                                clickOnItem = {
                                    clickOnItem(branch)
                                }
                            )
                        )
                    }
                }
            }
        }
    }


    override fun clickOnItem(element: NegoInfo, position: Int?) {
//        flowMainViewModel.let {
//            it.sucursal = element.sucursal
//            it.listOfStaffs = element.staffs
//            it.listOfServices = element.services
//        }
//        val action = AgendarSucursalFragmentDirections.actionAgendarSucursalToAgendarStaff()
//        navigate(action)

    }

    override fun clickOnAccept() {
//        findNavController().popBackStack()
    }

    override fun clickOnCancel() {

    }


}