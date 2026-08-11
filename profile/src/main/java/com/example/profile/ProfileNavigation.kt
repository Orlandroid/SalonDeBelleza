package com.example.profile


import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.core.navigation.AppNavigationRoutes
import com.example.core.profile.ProfileNavigationRoutes
import com.example.domain.CustomNavType
import com.example.domain.entities.local.AppointmentObject
import com.example.profile.contact_us.ContactUsScreen
import com.example.profile.historial_citas.AppointmentHistoryScreen
import com.example.profile.historial_detail.HistoryDetailScreen
import com.example.profile.profile.ProfileScreen
import com.example.profile.userprofile.UserProfileScreen
import kotlin.reflect.typeOf


fun NavGraphBuilder.profileNavigationGraph(navController: NavHostController) {
    navigation<AppNavigationRoutes.ProfileNavigationRoute>(
        startDestination = ProfileNavigationRoutes.ProfileRoute
    ) {
        composable<ProfileNavigationRoutes.ProfileRoute> {
            ProfileScreen(navController = navController, onCloseApp = {})
        }
        composable<ProfileNavigationRoutes.UserProfileRoute> {
            UserProfileScreen(navController = navController)
        }
        composable<ProfileNavigationRoutes.AppointmentHistoryRoute> {
            AppointmentHistoryScreen(navController = navController)
        }
        composable<ProfileNavigationRoutes.HistoryDetailRoute>(
            typeMap = mapOf(typeOf<AppointmentObject>() to CustomNavType.appointmentObject)
        ) {
            val arguments = it.toRoute<ProfileNavigationRoutes.HistoryDetailRoute>()
            HistoryDetailScreen(
                navController = navController,
                appointmentId = arguments.appointmentId
            )
        }
        composable<ProfileNavigationRoutes.ContactsRoute> {
            ContactUsScreen(navController)
        }
        composable<ProfileNavigationRoutes.TermsAndConditionsRoute> {
            Text("TermsAndConditions")
        }
    }
}