package com.example.citassalon.presentacion.features.schedule_appointment.detail_staff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.migration.Staff
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle


class DetalleStaffFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding) {

//    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
//        defaultViewModelProviderFactory
//    }

//    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
//        showToolbar = true,
//        toolbarTitle = "Detalle Staff"
//    )


    override fun setUpUi() {

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
//                DetailStaffView(flowMainViewModel.currentStaff)
            }
        }
    }

    @Composable
    fun DetailStaffView(staff: Staff) {
        var rating: Float by remember { mutableFloatStateOf(staff.valoracion.toFloat()) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            AsyncImage(
                model = staff.image_url,
                contentDescription = "ImageDetailStaff",
                modifier = Modifier
                    .height(150.dp)
                    .size(150.dp)
                    .padding(top = 24.dp)
            )
            Text(
                text = stringResource(id = R.string.nombre),
                Modifier
                    .padding(top = 32.dp),
                fontSize = 24.sp
            )
            Text(
                text = stringResource(id = R.string.evaluacion),
                Modifier.padding(top = 16.dp),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            RatingBar(
                value = rating,
                style = RatingBarStyle.Fill(),
                onValueChange = {
                    rating = it
                },
                onRatingChanged = {

                }
            )
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun DetailStaffViewPreview() {
//        DetailStaffView(flowMainViewModel.currentStaff)
    }

}