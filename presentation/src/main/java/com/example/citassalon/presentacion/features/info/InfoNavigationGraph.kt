package com.example.citassalon.presentacion.features.info


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.extensions.sharedViewModel
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.info.cart.CartScreen
import com.example.citassalon.presentacion.features.info.establishing.EstablishingScreen
import com.example.citassalon.presentacion.features.info.products.categories.CategoriesScreen
import com.example.citassalon.presentacion.features.info.products.products.ProductsScreen
import com.example.citassalon.presentacion.features.info.stores.StoresScreen
import com.example.citassalon.presentacion.features.schedule_appointment.branch.BranchesScreen


fun NavGraphBuilder.infoNavigationGraph(navController: NavHostController) {
    navigation<AppNavigationRoutes.InfoNavigationRoute>(
        startDestination = EstablishingRoute
    ) {
        composable<EstablishingRoute> {
            EstablishingScreen(navController = navController)
        }
        composable<StoresRoute> {
            StoresScreen(navController = navController)
        }
        composable<BranchesRoute> {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            BranchesScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable<CategoriesRoute> {
            CategoriesScreen(navController = navController)
        }
        composable<ProductsRoute> {
            ProductsScreen(navController = navController)
        }
        composable<CartRoute> {
            CartScreen(navController = navController)
        }
    }
}