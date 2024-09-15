package com.example.citassalon.presentacion.features.app_navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.citassalon.presentacion.features.auth.AuthNavigationGraph
import com.example.citassalon.presentacion.features.info.InfoNavigationGraph
import com.example.citassalon.presentacion.features.perfil.ProfileNavigationGraph
import com.example.citassalon.presentacion.features.schedule_appointment.ScheduleNavigationGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppNavigationRoutes.AuthNavigation.route
    ) {
        composable(route = AppNavigationRoutes.AuthNavigation.route) {
            AuthNavigationGraph(navController = navController) {
                navController.navigate(AppNavigationRoutes.ScheduleNavigation.route)
            }
        }
        composable(route = AppNavigationRoutes.ScheduleNavigation.route) {
            ScheduleNavigationGraph(
                navController =
                navController,
                goToInfoNavigation = {
                    navController.navigate(AppNavigationRoutes.InfoNavigation.route)
                },
                goToProfileNavigation = {
                    navController.navigate(AppNavigationRoutes.ProfileNavigation.route)
                }
            )
        }
        composable(route = AppNavigationRoutes.InfoNavigation.route) {
            InfoNavigationGraph(navController = navController)
        }
        composable(route = AppNavigationRoutes.ProfileNavigation.route) {
            ProfileNavigationGraph(navController = navController)
        }
    }
}