package com.example.citassalon.presentacion.features.app_navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.auth.authNavigationGraph
import com.example.citassalon.presentacion.features.schedule_appointment.scheduleNavigationGraph
import com.example.core.navigation.AppNavigationRoutes
import com.example.info.infoNavigationGraph
import com.example.profile.profileNavigationGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppNavigationRoutes.AuthNavigationRoute
    ) {
        authNavigationGraph(
            navController = navController,
            goToScheduleFlow = { navController.navigate(AppNavigationRoutes.ScheduleNavigationRoute) }
        )
        scheduleNavigationGraph(
            navController = navController,
            goToInfoNavigation = {
                navController.navigate(AppNavigationRoutes.InfoNavigationRoute)
            },
            goToProfileNavigation = {
                navController.navigate(AppNavigationRoutes.ProfileNavigationRoute)
            }
        )
        infoNavigationGraph(navController = navController)
        profileNavigationGraph(navController = navController)
    }
}