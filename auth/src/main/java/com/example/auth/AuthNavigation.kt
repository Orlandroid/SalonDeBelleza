package com.example.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.auth.login.LoginScreen
import com.example.auth.sign_up.SignUpScreen
import com.example.auth.splashscreen.SplashScreen
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
            LoginScreen(
                navController = navController,
                onCloseApplication = {})//Todo add onCloseApplication
        }
        composable<AuthNavigationRoutes.SingUpRoute> {
            SignUpScreen(navController)
        }
    }
}