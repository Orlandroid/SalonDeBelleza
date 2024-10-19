package com.example.citassalon.presentacion.features.profile.historial_citas

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.components.AppProgress
import com.example.citassalon.presentacion.features.extensions.GenericResultState
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.util.SwipeRecycler
import com.example.domain.mappers.toAppointmentObject
import com.example.domain.perfil.AppointmentFirebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistorialDeCitasFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    ClickOnItem<AppointmentFirebase>, SwipeRecycler.SwipeRecyclerListenr {


//    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
//        showToolbar = true, toolbarTitle = getString(R.string.historiasl_de_citas)
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DatingHistory()
            }
        }
    }

    @Composable
    fun DatingHistory(viewModel: HistorialCitasViewModel = hiltViewModel()) {
        val appointmentState = viewModel.appointment.observeAsState()
        val removeAppointmentState = viewModel.removeAppointment.observeAsState()
        val showProgress = remember { mutableStateOf(false) }
        if (showProgress.value) {
            Box(modifier = Modifier.fillMaxSize()) {
                AppProgress(Modifier.align(Alignment.Center))
            }
        } else {
            Column(Modifier.fillMaxSize()) {
                GenericResultState(
                    state = appointmentState,
                ) {
                    appointmentState.value?.data?.let { appointments ->
                        if (appointments.isEmpty()) {
                            NotDatView()
                        } else {
                            LazyColumn {
                                appointmentState.value?.data?.forEach { appointment ->
                                    item {
                                        ItemAppointment(appointment)
                                    }
                                }
                            }
                        }
                    }
                }
                GenericResultState(state = removeAppointmentState) {

                }
            }
        }
    }

    @Composable
    fun ItemAppointment(appointment: AppointmentFirebase) {
        androidx.compose.material3.Card(modifier = Modifier.padding(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = { }) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp),
                    painter = painterResource(id = R.drawable.tienda),
                    contentDescription = "ImageAppointment"
                )
                Text(text = appointment.service, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = appointment.establishment, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(color = Color.Red.copy(alpha = .80f),
                    text = "Remove",
                    fontSize = 24.sp,
                    modifier = Modifier.clickable {
                        shouldRemoveAppointment()
                    })
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun ItemAppointmentPreview() {
        val mAppointment = AppointmentFirebase(
            establishment = "establishment",
            service = "service",
        )
        ItemAppointment(
            appointment = mAppointment
        )
    }

    @Composable
    fun NotDatView() {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                getRandomNoDataAnimation()
            )
        )
        LottieAnimation(
            iterations = LottieConstants.IterateForever,
            composition = composition,
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
        )
    }


    override fun setUpUi() {
        //swipeRecycler.swipe(binding.recyclerAppointment, getListenerSwipeRecyclerListenr())
    }


    private fun getListener(): ClickOnItem<AppointmentFirebase> = this

    private fun getListenerSwipeRecyclerListenr() = this


    @SuppressLint("SetTextI18n")
    override fun observerViewModel() {
        super.observerViewModel()
//        observeApiResultGeneric(
//            liveData = viewModel.appointment,
//            onLoading = { binding.progressBar.visible() },
//            onFinishLoading = { binding.progressBar.gone() },
//            noData = {
////                with(binding) {
////                    imageNoData.visible()
////                    imageNoData.setAnimation(getRandomNoDataAnimation())
////                    imageNoData.playAnimation()
////                }
//            }) {
//            //binding.recyclerAppointment.adapter = historialCitasAdapter
//            //historialCitasAdapter.setData(it)
//        }
//        observeApiResultGeneric(
//            liveData = viewModel.removeAppointment,
//            hasProgressTheView = true,
//        ) {
//            showSuccessMessage("Appointment eliminado")
//        }


    }


    private fun getRandomNoDataAnimation(): Int = when ((1..3).random()) {
        1 -> {
            R.raw.no_data_animation
        }

        2 -> {
            R.raw.no_data_available
        }

        else -> R.raw.no_data_found
    }


    override fun clickOnItem(element: AppointmentFirebase, position: Int?) {
        val appointment = element.toAppointmentObject()
//        val action =
//            HistorialDeCitasFragmentDirections.actionHistorialDeCitasToHistorialDetailFragment(
//                appointment.toJson()
//            )
//        findNavController().navigate(action)
    }

    override fun onMove() {


    }

    private fun shouldRemoveAppointment() {
        val alert = AlertDialogs(
            AlertDialogs.WARNING_MESSAGE,
            "Estas seguro que deseas eliminar el registro",
            object : AlertDialogs.ClickOnAccept {
                override fun clickOnAccept() {
//                    val appointment = historialCitasAdapter.getElement(position)
//                    viewModel.removeAppointment(appointment.idAppointment)
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun clickOnCancel() {
//                    historialCitasAdapter.notifyDataSetChanged()
                }
            },
            isTwoButtonDialog = true
        )
        activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
    }

    override fun onSwipe(position: Int) {
        shouldRemoveAppointment()
    }


}