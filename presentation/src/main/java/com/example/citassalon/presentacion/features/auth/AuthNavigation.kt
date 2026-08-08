package com.example.citassalon.presentacion.features.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.citassalon.presentacion.features.auth.login.LoginScreen
import com.example.citassalon.presentacion.features.auth.sign_up.SignUpScreen
import com.example.citassalon.presentacion.features.auth.splashscreen.SplashScreen
import com.example.core.auth.AuthNavigationRoutes
import com.example.core.navigation.AppNavigationRoutes


fun NavGraphBuilder.authNavigationGraph(
    navController: NavHostController,
    goToScheduleFlow: () -> Unit
) {
    navigation<AppNavigationRoutes.AuthNavigationRoute>(
        startDestination = AuthNavigationRoutes.SplashRoute
    ) {
        composable<AuthNavigationRoutes.SplashRoute> {
            SplashScreen(
                navController = navController,
                goToScheduleNav = goToScheduleFlow
            )
        }
        composable<AuthNavigationRoutes.LoginRoute> {
            LoginScreen(navController = navController)
        }
        composable<AuthNavigationRoutes.SingUpRoute> {
            SignUpScreen(navController)
        }
    }
}