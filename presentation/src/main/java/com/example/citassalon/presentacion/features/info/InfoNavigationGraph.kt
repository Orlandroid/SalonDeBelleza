package com.example.citassalon.presentacion.features.info


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun InfoNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "") {
        composable(route = InfoNavigationScreens.EstablishingScreen.route) {
            Text(text = "InfoNavigationGraph")
        }
    }
}