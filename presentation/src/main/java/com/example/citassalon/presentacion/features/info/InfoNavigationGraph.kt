package com.example.citassalon.presentacion.features.info


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.extensions.sharedViewModel
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.info.cart.CartScreen
import com.example.citassalon.presentacion.features.info.establishing.EstablishingScreen
import com.example.citassalon.presentacion.features.info.nuestro_staff.OurStaffScreen
import com.example.citassalon.presentacion.features.info.products.categories.CategoriesScreen
import com.example.citassalon.presentacion.features.info.products.detalleproducto.DetailProductScreen
import com.example.citassalon.presentacion.features.info.products.products.ProductsScreen
import com.example.citassalon.presentacion.features.info.services.ServicesScreen
import com.example.citassalon.presentacion.features.info.stores.StoresScreen
import com.example.citassalon.presentacion.features.info.sucursal.BranchInfoScreen
import com.example.citassalon.presentacion.features.info.ubicacion.LocationScreen
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BranchesScreen
import com.example.citassalon.presentacion.features.schedule_appointment.branches.Flow
import com.example.domain.entities.remote.CustomNavType
import com.example.domain.entities.remote.Product
import kotlin.reflect.typeOf


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
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            BranchesScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                flow = Flow.INFO
            )
        }
        composable<InfoNavigationScreens.CategoriesRoute> {
            CategoriesScreen(navController = navController)
        }
        composable<InfoNavigationScreens.ProductsRoute> {
            val arguments = it.toRoute<InfoNavigationScreens.ProductsRoute>()
            ProductsScreen(navController = navController, category = arguments.category)
        }
        composable<InfoNavigationScreens.DetailProductRoute>(
            typeMap = mapOf(typeOf<Product>() to CustomNavType.productType)
        ) {
            val arguments = it.toRoute<InfoNavigationScreens.DetailProductRoute>()
            DetailProductScreen(navController = navController, product = arguments.product)
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