package com.example.info


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.core.navigation.info.InfoNavigationScreens
import com.example.core.navigation.AppNavigationRoutes
import com.example.info.cart.CartScreen
import com.example.info.establishing.EstablishingScreen
import com.example.info.nuestro_staff.OurStaffScreen
import com.example.info.products.categories.CategoriesScreen
import com.example.info.products.detalleproducto.DetailProductScreen
import com.example.info.products.products.ProductsScreen
import com.example.info.services.ServicesScreen
import com.example.info.stores.StoresScreen
import com.example.info.sucursal.BranchInfoScreen
import com.example.info.ubicacion.LocationScreen


fun NavGraphBuilder.infoNavigationGraph(navController: NavHostController) {
    navigation<AppNavigationRoutes.InfoNavigationRoute>(
        startDestination = InfoNavigationScreens.EstablishingRoute
    ) {
        composable<InfoNavigationScreens.EstablishingRoute> {
            EstablishingScreen(navController = navController)
        }
        composable<InfoNavigationScreens.StoresRoute> {
            StoresScreen(navController = navController)
        }
        composable<InfoNavigationScreens.BranchesRoute> {
//            val mainViewModel =
//                it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
//            mainViewModel.currentFlowBranch = BranchFlow.INFO
//            BranchesScreen(
//                navController = navController,
//                mainViewModel = mainViewModel
//            )
        }
        composable<InfoNavigationScreens.CategoriesRoute> {
            val arguments = it.toRoute<InfoNavigationScreens.CategoriesRoute>()
            CategoriesScreen(
                navController = navController,
                categorySource = arguments.source
            )
        }
        composable<InfoNavigationScreens.ProductsRoute> {
            val arguments = it.toRoute<InfoNavigationScreens.ProductsRoute>()
            ProductsScreen(
                navController = navController,
                source = arguments.source,
                category = arguments.category
            )
        }
        composable<InfoNavigationScreens.DetailProductRoute> {
            val arguments = it.toRoute<InfoNavigationScreens.DetailProductRoute>()
            DetailProductScreen(
                navController = navController,
                productId = arguments.productId,
                source = arguments.source
            )
        }
        composable<InfoNavigationScreens.CartRoute> {
            CartScreen(navController = navController)
        }
        composable<InfoNavigationScreens.BranchInfoRoute> {
            BranchInfoScreen(navController = navController)
        }
        composable<InfoNavigationScreens.OurStaffRoute> {
            OurStaffScreen(navController = navController)
        }
        composable<InfoNavigationScreens.ServicesRoute> {
            ServicesScreen(navController = navController)
        }
        composable<InfoNavigationScreens.LocationRoute> {
            LocationScreen(navController = navController)
        }
    }
}