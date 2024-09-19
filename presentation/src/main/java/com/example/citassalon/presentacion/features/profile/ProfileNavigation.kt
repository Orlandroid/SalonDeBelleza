package com.example.citassalon.presentacion.features.profile


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.profile.historial_citas.AppointmentHistoryScreen
import com.example.citassalon.presentacion.features.profile.perfil.ProfileScreen
import com.example.citassalon.presentacion.features.profile.userprofile.UserProfileScreen


fun NavGraphBuilder.profileNavigationGraph(navController: NavHostController) {
    navigation(
        route = AppNavigationRoutes.ProfileNavigation.route,
        startDestination = ProfileNavigationScreen.Profile.route
    ) {
        composable(route = ProfileNavigationScreen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = ProfileNavigationScreen.UserProfile.route) {
            UserProfileScreen(navController = navController)
        }
        composable(route = ProfileNavigationScreen.AppointmentHistory.route) {
            AppointmentHistoryScreen(navController = navController)
        }
    }
}