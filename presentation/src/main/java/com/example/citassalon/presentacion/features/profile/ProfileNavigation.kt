package com.example.citassalon.presentacion.features.profile


import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.profile.contact_us.ContactUsScreen
import com.example.citassalon.presentacion.features.profile.historial_citas.AppointmentHistoryScreen
import com.example.citassalon.presentacion.features.profile.historial_detail.HistoryDetailScreen
import com.example.citassalon.presentacion.features.profile.profile.ProfileScreen
import com.example.citassalon.presentacion.features.profile.userprofile.UserProfileScreen
import com.example.domain.entities.local.AppointmentObject
import com.example.domain.CustomNavType
import kotlin.reflect.typeOf


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
        composable<ProfileNavigationScreen.HistoryDetailRoute>(
            typeMap = mapOf(typeOf<AppointmentObject>() to CustomNavType.appointmentObject)
        ) {
            val arguments = it.toRoute<ProfileNavigationScreen.HistoryDetailRoute>()
            HistoryDetailScreen(
                navController = navController,
                appointmentId = arguments.appointmentId
            )
        }
        composable<ProfileNavigationScreen.ContactsRoute> {
            ContactUsScreen(navController)
        }
        composable<ProfileNavigationScreen.TermsAndConditionsRoute> {
            Text("TermsAndConditions")
        }
    }
}