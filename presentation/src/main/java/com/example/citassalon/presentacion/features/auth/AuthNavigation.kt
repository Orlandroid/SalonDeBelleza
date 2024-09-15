package com.example.citassalon.presentacion.features.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.citassalon.presentacion.features.auth.login.LoginScreen
import com.example.citassalon.presentacion.features.auth.login.LoginViewModel
import com.example.citassalon.presentacion.features.auth.sign_up.SignUpScreen
import com.example.citassalon.presentacion.features.auth.splashscreen.SplashScreenV1


@Composable
fun AuthNavigationGraph(navController: NavHostController, goToScheduleNav: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = AuthScreens.SplashScreen.route
    ) {
        composable(route = AuthScreens.SplashScreen.route) {
//            val loginViewModel: LoginViewModel = hiltViewModel()
            SplashScreenV1(navController, true)
        }
//        composable(route = AuthScreens.LoginScreen.route) {
//            LoginScreen(navController) {
//                goToScheduleNav()
//            }
//        }
//        composable(route = AuthScreens.SingUpScreen.route) {
//            SignUpScreen(navController)
//        }
    }
}