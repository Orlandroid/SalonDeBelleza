package com.example.citassalon.presentacion.features.schedule_appointment.detail_staff

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.theme.Background
import com.example.domain.entities.remote.migration.Staff
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle

@Composable
fun DetailStaffScreen(
    navController: NavController,
    currentStaff: Staff
) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.detail_staff))
    ) {
        DetailStaffScreenContent(modifier = Modifier, staff = currentStaff)
    }
}

@Composable
fun DetailStaffScreenContent(
    modifier: Modifier = Modifier, staff: Staff
) {
    var rating: Float by remember { mutableFloatStateOf(staff.valoracion.toFloat()) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
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
            text = staff.nombre, Modifier.padding(top = 32.dp), fontSize = 24.sp
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
fun DetailStaffScreenPreview() {
    DetailStaffScreenContent(modifier = Modifier, staff = Staff.mockStaff())
}