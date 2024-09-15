package com.example.citassalon.presentacion.features.perfil


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.citassalon.presentacion.features.perfil.historial_citas.AppointmentHistoryScreen
import com.example.citassalon.presentacion.features.perfil.perfil.ProfileScreen
import com.example.citassalon.presentacion.features.perfil.userprofile.UserProfileScreen
import com.example.citassalon.presentacion.features.perfil.userprofile.UserProfileViewModel

@Composable
fun ProfileNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ProfileNavigationScreen.Profile.route
    ) {
        composable(route = ProfileNavigationScreen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = ProfileNavigationScreen.UserProfile.route) {
            val userProfileViewModel: UserProfileViewModel = hiltViewModel()
            val infoUserState = userProfileViewModel.infoUser.observeAsState()
            val imageUserState = userProfileViewModel.imageUser.observeAsState()
            val imageUserProfileState = userProfileViewModel.imageUserProfile.observeAsState()
            LaunchedEffect(Unit) {
                userProfileViewModel.getUserInfo()
                userProfileViewModel.getUserImage()
            }
            UserProfileScreen(
                saveImageUser = { base64Image ->
                    userProfileViewModel.saveImageUser(base64Image)
                },
                infoUserState = infoUserState,
                imageUserState = imageUserState,
                imageUserProfileState = imageUserProfileState,
                money = userProfileViewModel.getUserMoney(),
                navController = navController
            )
        }
        composable(route = ProfileNavigationScreen.AppointmentHistory.route) {
            AppointmentHistoryScreen(navController = navController)
        }
    }
}