package com.example.citassalon.presentacion.features.navigation


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.citassalon.presentacion.features.home.HomeScreen
import com.example.citassalon.presentacion.features.login.LoginScreen
import com.example.citassalon.presentacion.features.login.LoginViewModel
import com.example.citassalon.presentacion.features.perfil.historial_citas.AppointmentHistoryScreen
import com.example.citassalon.presentacion.features.perfil.perfil.ProfileScreen
import com.example.citassalon.presentacion.features.perfil.userprofile.UserProfileScreen
import com.example.citassalon.presentacion.features.perfil.userprofile.UserProfileViewModel
import com.example.citassalon.presentacion.features.sign_up.SignUpScreen
import com.example.citassalon.presentacion.features.splashscreen.SplashScreenV1

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.SplashScreen.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            SplashScreenV1(navController, loginViewModel.getUserSession())
        }
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = Screens.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = Screens.UserProfileScreen.route) {
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
        composable(route = Screens.AppointmentHistoryScreen.route) {
            AppointmentHistoryScreen(navController = navController)
        }
        composable(route = Screens.ContactScreen.route) {
            Text(text = "ContactScreen")
        }
        composable(route = Screens.TermsAndConditions.route) {
            Text(text = "TermsAndConditions")
        }
        composable(route = Screens.SingUpScreen.route) {
            SignUpScreen(navController)
        }
    }
}