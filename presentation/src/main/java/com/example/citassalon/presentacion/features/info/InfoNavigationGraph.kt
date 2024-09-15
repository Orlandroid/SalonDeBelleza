package com.example.citassalon.presentacion.features.info


import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes


fun NavGraphBuilder.infoNavigationGraph(navController: NavHostController) {
    navigation(
        route = AppNavigationRoutes.InfoNavigation.route,
        startDestination = InfoNavigationScreens.EstablishingScreen.route
    ) {
        composable(route = InfoNavigationScreens.EstablishingScreen.route) {
            Text(text = "InfoNavigationGraph")
        }
    }
}