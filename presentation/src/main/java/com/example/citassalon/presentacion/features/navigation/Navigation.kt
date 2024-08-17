package com.example.citassalon.presentacion.features.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.citassalon.presentacion.features.home.HomeScreen
import com.example.citassalon.presentacion.features.login.LoginScreen
import com.example.citassalon.presentacion.features.perfil.perfil.PerfilViewModel
import com.example.citassalon.presentacion.features.perfil.perfil.ProfileScreen
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
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = Screens.ProfileScreen.route) {
            val profileViewModel: PerfilViewModel = hiltViewModel()
            val firebaseUser = profileViewModel.firebaseUser.observeAsState()
            ProfileScreen(
                navController = navController,
                firebaseEmail = firebaseUser.value?.email,
                profileViewModel.setElementsMenu()
            )
        }
    }
}