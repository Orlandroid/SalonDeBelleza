package com.example.citassalon.presentacion.features.auth

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.auth.login.LoginScreen
import com.example.citassalon.presentacion.features.auth.login.LoginViewModel
import com.example.citassalon.presentacion.features.auth.sign_up.SignUpScreen
import com.example.citassalon.presentacion.features.auth.splashscreen.SplashScreenV1


fun NavGraphBuilder.authNavigationGraph(
    navController: NavHostController,
    goToScheduleNav: () -> Unit
) {
    navigation(
        route = AppNavigationRoutes.AuthNavigation.route,
        startDestination = AuthScreens.SplashScreen.route,
    ) {
        composable(route = AuthScreens.SplashScreen.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            SplashScreenV1(
                navController = navController,
                isActiveSession = loginViewModel.getUserSession()
            ) {
                goToScheduleNav.invoke()
            }
        }
        composable(route = AuthScreens.LoginScreen.route) {
            LoginScreen(navController) {
                goToScheduleNav()
            }
        }
        composable(route = AuthScreens.SingUpScreen.route) {
            SignUpScreen(navController)
        }
    }
}