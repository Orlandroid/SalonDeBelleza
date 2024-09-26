package com.example.citassalon.presentacion.features.profile


import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.profile.historial_citas.AppointmentHistoryScreen
import com.example.citassalon.presentacion.features.profile.perfil.ProfileScreen
import com.example.citassalon.presentacion.features.profile.userprofile.UserProfileScreen


fun NavGraphBuilder.profileNavigationGraph(navController: NavHostController) {
    navigation<AppNavigationRoutes.ProfileNavigationRoute>(
        startDestination = ProfileNavigationScreen.ProfileRoute
    ) {
        composable<ProfileNavigationScreen.ProfileRoute> {
            ProfileScreen(navController = navController)
        }
        composable<ProfileNavigationScreen.UserProfileRoute> {
            UserProfileScreen(navController = navController)
        }
        composable<ProfileNavigationScreen.AppointmentHistoryRoute> {
            AppointmentHistoryScreen(navController = navController)
        }
        composable<ProfileNavigationScreen.ContactsRoute> {
            Text("Contacts")
        }
        composable<ProfileNavigationScreen.TermsAndConditionsRoute> {
            Text("TermsAndConditions")
        }
    }
}