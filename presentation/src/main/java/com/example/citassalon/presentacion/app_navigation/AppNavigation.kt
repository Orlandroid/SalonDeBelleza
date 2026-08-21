package com.example.citassalon.presentacion.app_navigation


import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.auth.authNavigationGraph
import com.example.citassalon.presentacion.MainActivityCompose
import com.example.core.navigation.AppNavigationRoutes
import com.example.info.infoNavigationGraph
import com.example.profile.profileNavigationGraph
import com.example.scheduleappointment.scheduleNavigationGraph
import com.example.wallet.walletNavigationGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val activity = LocalActivity.current as MainActivityCompose
    NavHost(
        navController = navController,
        startDestination = AppNavigationRoutes.AuthNavigationRoute
    ) {
        authNavigationGraph(
            navController = navController,
            goToScheduleFlow = { navController.navigate(AppNavigationRoutes.ScheduleNavigationRoute) },
            onRestart = { activity.closeAndOpenActivity() }
        )
        scheduleNavigationGraph(
            navController = navController,
            goToInfoNavigation = {
                navController.navigate(AppNavigationRoutes.InfoNavigationRoute)
            },
            goToProfileNavigation = {
                navController.navigate(AppNavigationRoutes.ProfileNavigationRoute)
            },
            goToWallet = {
                navController.navigate(AppNavigationRoutes.WalletNavigationRoute)
            },
            onRestart = { activity.closeAndOpenActivity() }
        )
        infoNavigationGraph(navController = navController)
        profileNavigationGraph(
            navController = navController,
            onRestart = { activity.closeAndOpenActivity() }
        )
        walletNavigationGraph(
            navController = navController,
            onRestart = { activity.closeAndOpenActivity() })
    }
}