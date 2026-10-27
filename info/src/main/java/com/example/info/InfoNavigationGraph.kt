package com.example.info

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.branches.branches.BranchesScreen
import com.example.core.navigation.AppNavigationRoutes
import com.example.core.navigation.info.InfoNavigationRoutes
import com.example.core.ui.components.SuccessScreen
import com.example.info.cart.CartScreen
import com.example.info.establishing.EstablishingScreen
import com.example.info.oursttaf.OurStaffScreen
import com.example.info.products.categories.CategoriesScreen
import com.example.info.products.detalleproducto.DetailProductScreen
import com.example.info.products.products.ProductsScreen
import com.example.info.services.ServicesScreen
import com.example.info.stores.StoresScreen
import com.example.info.sucursal.BranchInfoScreen
import com.example.info.ubicacion.LocationScreen

fun NavGraphBuilder.infoNavigationGraph(navController: NavHostController) {
    navigation<AppNavigationRoutes.InfoNavigationRoute>(
        startDestination = InfoNavigationRoutes.EstablishingRoute
    ) {
        composable<InfoNavigationRoutes.EstablishingRoute> {
            EstablishingScreen(navController = navController)
        }
        composable<InfoNavigationRoutes.StoresRoute> {
            StoresScreen(navController = navController)
        }
        composable<InfoNavigationRoutes.BranchesRoute> {
            BranchesScreen(
                navController = navController
            )
        }
        composable<InfoNavigationRoutes.CategoriesRoute> {
            val arguments = it.toRoute<InfoNavigationRoutes.CategoriesRoute>()
            CategoriesScreen(
                navController = navController,
                categorySource = arguments.source
            )
        }
        composable<InfoNavigationRoutes.ProductsRoute> {
            val arguments = it.toRoute<InfoNavigationRoutes.ProductsRoute>()
            ProductsScreen(
                navController = navController,
                source = arguments.source,
                category = arguments.category
            )
        }
        composable<InfoNavigationRoutes.DetailProductRoute> {
            val arguments = it.toRoute<InfoNavigationRoutes.DetailProductRoute>()
            DetailProductScreen(
                navController = navController,
                productId = arguments.productId,
                source = arguments.source
            )
        }
        composable<InfoNavigationRoutes.CartRoute> {
            CartScreen(navController = navController)
        }
        composable<InfoNavigationRoutes.BranchInfoRoute> {
            BranchInfoScreen(navController = navController)
        }
        composable<InfoNavigationRoutes.OurStaffRoute> {
            OurStaffScreen(navController = navController)
        }
        composable<InfoNavigationRoutes.ServicesRoute> {
            ServicesScreen(navController = navController)
        }
        composable<InfoNavigationRoutes.LocationRoute> {
            LocationScreen(navController = navController)
        }
        composable<InfoNavigationRoutes.SuccessScreenRoute> {
            SuccessScreen(
                title = "Purchase successful!",
                description = "Your order has been successfully placed."
            ) {
                navController.popBackStack()
                navController.popBackStack()
            }
        }
    }
}
