package com.example.citassalon.presentacion.features.app_navigation


import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.citassalon.presentacion.features.auth.authNavigationGraph
import com.example.citassalon.presentacion.features.info.infoNavigationGraph
import com.example.citassalon.presentacion.features.profile.profileNavigationGraph
import com.example.citassalon.presentacion.features.schedule_appointment.scheduleNavigationGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val activity = LocalContext.current as Activity
    NavHost(
        navController = navController,
        startDestination = AppNavigationRoutes.AuthNavigationRoute
    ) {
        authNavigationGraph(
            navController = navController,
            goToScheduleFlow = { navController.navigate(AppNavigationRoutes.ScheduleNavigationRoute) },
            closeActivity = {
                (activity as MainActivityCompose).finish()
            }
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