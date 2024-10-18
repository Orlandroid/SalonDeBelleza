package com.example.citassalon.presentacion.features.schedule_appointment.staff


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.components.ItemStaff
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.domain.entities.remote.migration.Staff
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarStaffFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    AlertDialogs.ClickOnAccept {


//    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
//        defaultViewModelProviderFactory
//    }


//    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
//        showToolbar = true, toolbarTitle = getString(R.string.agendar_staff)
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ScheduleStaff()
            }
        }
    }

    @Composable
    fun ScheduleStaff() {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "flowMainViewModel.sucursal.name",
                fontSize = 32.sp,
                style = TextStyle(fontWeight = FontWeight.W900)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
//                val randomStaff = getRandomStaff(flowMainViewModel.listOfStaffs)
//                flowMainViewModel.currentStaff = randomStaff
//                navigate(AgendarStaffFragmentDirections.actionAgendarStaffToAgendarServicio())
            }) {
                Text(text = stringResource(id = R.string.estilista_button))
            }
            Spacer(modifier = Modifier.height(32.dp))
            LazyColumn {
//                flowMainViewModel.listOf
                //
                //                Staffs.forEach { myStaff ->
//                    item {
//                        ItemStaff(
//                            staff = myStaff,
//                            onClick = {
//                                clickOnStaff(it, myStaff)
//                            }
//                        )
//                    }
//                }
            }
        }
    }

//    private fun clickOnStaff(
//        clickOnItemStaff: ClickOnItemStaff,
//        myStaff: Staff
//    ) {
//        flowMainViewModel.currentStaff = myStaff
//        when (clickOnItemStaff) {
//            ClickOnItemStaff.ClickOnItem -> {
//                navigate(AgendarStaffFragmentDirections.actionAgendarStaffToAgendarServicio())
//            }
//
//            ClickOnItemStaff.ClickOnImage -> {
//                navigate(AgendarStaffFragmentDirections.actionAgendarStaffToDetalleStaff())
//            }
//        }
//    }

    private fun getRandomStaff(staffs: List<Staff>): Staff {
        val randomNumber = staffs.indices.random()
        val randomStaff = staffs[randomNumber]
        return randomStaff
    }

    @Composable
    @Preview(showBackground = true)
    fun ItemStaffPreview(modifier: Modifier = Modifier) {
        ItemStaff(
            staff = Staff(
                id = "", image_url = "", nombre = "", sexo = "", valoracion = 4
            )
        )
    }


    override fun setUpUi() {

    }

    override fun clickOnAccept() {
//        findNavController().popBackStack()
    }

    override fun clickOnCancel() {

    }


}