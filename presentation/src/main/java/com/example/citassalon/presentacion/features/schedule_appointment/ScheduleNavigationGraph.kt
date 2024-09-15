package com.example.citassalon.presentacion.features.schedule_appointment

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.citassalon.presentacion.features.schedule_appointment.home.HomeScreen


@Composable
fun ScheduleNavigationGraph(
    navController: NavHostController,
    goToInfoNavigation: () -> Unit,
    goToProfileNavigation: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ScheduleAppointmentScreens.HomeScreen.route
    ) {
        composable(route = ScheduleAppointmentScreens.HomeScreen.route) {
            HomeScreen(
                navController = navController,
                goToInfoNavigation = goToInfoNavigation,
                goToProfileNavigation = goToProfileNavigation
            )
        }
    }
}