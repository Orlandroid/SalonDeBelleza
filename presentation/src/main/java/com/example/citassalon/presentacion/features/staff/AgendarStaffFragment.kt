package com.example.citassalon.presentacion.features.staff


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarStaffBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.domain.entities.remote.migration.Staff
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarStaffFragment :
    BaseFragment<FragmentAgendarStaffBinding>(R.layout.fragment_generic_binding),
    ClickOnItem<Staff>,
    AlertDialogs.ClickOnAccept {

        
    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }


    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = "Agendar Staff"
    )

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
                text = flowMainViewModel.sucursal.name,
                fontSize = 32.sp,
                style = TextStyle(fontWeight = FontWeight.W900)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val randomStaff = getRandomStaff(flowMainViewModel.listOfStaffs)
                    flowMainViewModel.currentStaff = randomStaff
                    navigate(AgendarStaffFragmentDirections.actionAgendarStaffToAgendarServicio())
                }
            ) {
                Text(text = stringResource(id = R.string.estilista_button))
            }
            Spacer(modifier = Modifier.height(32.dp))
            LazyColumn {
                flowMainViewModel.listOfStaffs.forEach { myStaff ->
                    item {
                        ItemStaff(staff = myStaff)
                    }
                }
            }
        }
    }

    private fun getRandomStaff(staffs: List<Staff>): Staff {
        val randomNumber = staffs.indices.random()
        val randomStaff = staffs[randomNumber]
        return randomStaff
    }

    @Composable
    fun ItemStaff(modifier: Modifier = Modifier, staff: Staff) {
        Card(
            onClick = {
                flowMainViewModel.currentStaff = staff
                navigate(AgendarStaffFragmentDirections.actionAgendarStaffToAgendarServicio())
            },
            modifier
                .fillMaxWidth()
                .padding(8.dp),

            ) {
            Column(
                Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val imageLoader =
                    LocalContext.current.imageLoader.newBuilder().logger(DebugLogger()).build()
                AsyncImage(
                    modifier = Modifier.size(150.dp),
                    model = staff.image_url,
                    contentDescription = "ImageStaff",
                    imageLoader = imageLoader
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = staff.nombre, fontWeight = FontWeight.W900, fontSize = 18.sp)
            }
        }
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


    override fun clickOnItem(element: Staff, position: Int?) {
        flowMainViewModel.currentStaff = element
        if (position == 0) {
            val action = AgendarStaffFragmentDirections.actionAgendarStaffToDetalleStaff()
            navigate(action)
            return
        }
        navigate(AgendarStaffFragmentDirections.actionAgendarStaffToAgendarServicio())
    }

    override fun clickOnAccept() {
        findNavController().popBackStack()
    }

    override fun clickOnCancel() {

    }


}