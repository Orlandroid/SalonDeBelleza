package com.example.citassalon.presentacion.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.citassalon.presentacion.features.login.LoginScreen
import com.example.citassalon.presentacion.features.splashscreen.SplashScreenV1

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.SplashScreen.route) {
            SplashScreenV1(navController)
        }
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController)
        }
    }
}