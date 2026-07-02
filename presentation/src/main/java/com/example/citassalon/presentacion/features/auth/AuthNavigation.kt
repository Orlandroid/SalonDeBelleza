package com.example.citassalon.presentacion.features.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.auth.login.LoginScreen
import com.example.citassalon.presentacion.features.auth.sign_up.SignUpScreen
import com.example.citassalon.presentacion.features.auth.splashscreen.SplashScreen


fun NavGraphBuilder.authNavigationGraph(
    navController: NavHostController,
    goToScheduleFlow: () -> Unit
) {
    navigation<AppNavigationRoutes.AuthNavigationRoute>(
        startDestination = AuthScreens.SplashRoute
    ) {
        composable<AuthScreens.SplashRoute> {
            SplashScreen(
                navController = navController,
                goToScheduleNav = goToScheduleFlow
            )
        }
        composable<AuthScreens.LoginRoute> {
            LoginScreen(navController = navController)
        }
        composable<AuthScreens.SingUpRoute> {
            SignUpScreen(navController)
        }
    }
}