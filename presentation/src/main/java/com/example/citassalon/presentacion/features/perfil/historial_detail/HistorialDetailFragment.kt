package com.example.citassalon.presentacion.features.perfil.historial_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.fromJson
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.local.AppointmentObject


class HistorialDetailFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding) {

    private val args: HistorialDetailFragmentArgs by navArgs()
    private lateinit var appointment: AppointmentObject

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = "Historial"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                appointment = args.appointment.fromJson()
                HistoryDetail(appointment)
            }
        }
    }

    @Composable
    fun HistoryDetail(appointmentObject: AppointmentObject) {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            val (cardContent) = createRefs()
            Card(
                Modifier
                    .padding(all = 8.dp)
                    .constrainAs(cardContent) {
                        width = Dimension.matchParent
                        height = Dimension.wrapContent
                        linkTo(parent.start, parent.end)
                        linkTo(parent.top, parent.bottom)
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.tienda),
                        contentDescription = "ImageEstablecimiento"
                    )
                    TextHistory(text = "Establecimiento: ${appointmentObject.establishment}")
                    TextHistory(text = "Empleado: ${appointment.employee}")
                    TextHistory(text = "Servicio: ${appointment.service}")
                    TextHistory(text = "Hora: ${appointment.hour}")
                    TextHistory(text = "Fecha: ${appointment.date}")
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    @Composable
    fun TextHistory(text: String) {
        Text(
            textAlign = TextAlign.Center,
            text = text,
            color = Color.Black,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun setUpUi() {/*
        appointment = args.appointment.fromJson()
        with(binding) {
            tvEstablecimiento.text = "Establecimiento: ${appointment.establishment}"
            tvEmpleado.text = "Empleado: ${appointment.employee}"
            tvServicio.text = "Servicio: ${appointment.service}"
            tvHora.text = "Hora: ${appointment.hour}"
            tvFecha.text = "Fecha: ${appointment.date}"
        }*/
    }

}