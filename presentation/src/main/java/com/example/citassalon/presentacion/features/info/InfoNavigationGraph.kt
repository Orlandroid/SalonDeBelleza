package com.example.citassalon.presentacion.features.info


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.info.establecimiento.EstablishingScreen


fun NavGraphBuilder.infoNavigationGraph(navController: NavHostController) {
    navigation(
        route = AppNavigationRoutes.InfoNavigation.route,
        startDestination = InfoNavigationScreens.Establishing.route
    ) {
        composable(route = InfoNavigationScreens.Establishing.route) {
            EstablishingScreen(navController = navController)
        }
    }
}