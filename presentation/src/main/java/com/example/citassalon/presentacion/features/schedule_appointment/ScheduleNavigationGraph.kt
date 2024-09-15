package com.example.citassalon.presentacion.features.schedule_appointment

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.schedule_appointment.home.HomeScreen


fun NavGraphBuilder.scheduleNavigationGraph(
    navController: NavHostController,
    goToInfoNavigation: () -> Unit,
    goToProfileNavigation: () -> Unit
) {
    navigation(
        route = AppNavigationRoutes.ScheduleNavigation.route,
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