package com.example.citassalon.presentacion.features.profile.historial_detail

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.local.AppointmentObject

@Composable
fun HistoryDetail(
    navController: NavController,
    appointment: AppointmentObject
) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.history_detail))
    ) {
        HistoryDetailContent(appointment = appointment)
    }

}

@Composable
fun HistoryDetailContent(
    modifier: Modifier = Modifier,
    appointment: AppointmentObject
) {
    ConstraintLayout(
        modifier
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
                TextHistory(text = "Establecimiento: ${appointment.establishment}")
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


@Composable
@Preview(showBackground = true)
fun HistoryDetailContentPreview() {
    HistoryDetailContent(
        appointment = AppointmentObject(
            establishment = "Zacatecas",
            employee = "Diego",
            service = "Corte de pelo",
            hour = "16:00 pm",
            date = "01/01/202"
        )
    )
}


