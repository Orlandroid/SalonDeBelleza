package com.example.citassalon.presentacion.features.app_navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.citassalon.presentacion.features.auth.authNavigationGraph
import com.example.citassalon.presentacion.features.info.infoNavigationGraph
import com.example.citassalon.presentacion.features.profile.profileNavigationGraph
import com.example.citassalon.presentacion.features.schedule_appointment.scheduleNavigationGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppNavigationRoutes.AuthNavigation.route
    ) {
        authNavigationGraph(navController = navController) {
            navController.navigate(AppNavigationRoutes.ScheduleNavigation.route)
        }
        scheduleNavigationGraph(
            navController = navController,
            goToInfoNavigation = {
                navController.navigate(AppNavigationRoutes.InfoNavigation.route)
            },
            goToProfileNavigation = {
                navController.navigate(AppNavigationRoutes.ProfileNavigation.route)
            }
        )
        infoNavigationGraph(navController = navController)
        profileNavigationGraph(navController = navController)
    }
}