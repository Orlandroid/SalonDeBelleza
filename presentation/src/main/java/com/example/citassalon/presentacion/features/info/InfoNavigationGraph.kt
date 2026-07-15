package com.example.citassalon.presentacion.features.info


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.extensions.sharedViewModel
import com.example.citassalon.presentacion.features.info.cart.CartScreen
import com.example.citassalon.presentacion.features.info.contactus.ContactUsScreen
import com.example.citassalon.presentacion.features.info.establishing.EstablishingScreen
import com.example.citassalon.presentacion.features.info.nuestro_staff.OurStaffScreen
import com.example.citassalon.presentacion.features.info.products.categories.CategoriesScreen
import com.example.citassalon.presentacion.features.info.products.detalleproducto.DetailProductScreen
import com.example.citassalon.presentacion.features.info.products.products.ProductsScreen
import com.example.citassalon.presentacion.features.info.services.ServicesScreen
import com.example.citassalon.presentacion.features.info.stores.StoresScreen
import com.example.citassalon.presentacion.features.info.sucursal.BranchInfoScreen
import com.example.citassalon.presentacion.features.info.ubicacion.LocationScreen
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BranchFlow
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BranchesScreen
import com.example.citassalon.presentacion.features.schedule_appointment.mainflow.AppointmentFlowViewModel
import com.example.data.remote.products.fakestore.FakeStoreProduct
import com.example.domain.CustomNavType
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
            val mainViewModel =
                it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            mainViewModel.currentFlowBranch = BranchFlow.INFO
            BranchesScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
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
        composable<InfoNavigationScreens.DetailProductRoute>(
            typeMap = mapOf(typeOf<FakeStoreProduct>() to CustomNavType.productType)
        ) {
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
        composable<InfoNavigationScreens.ContactUsRoute> {
            ContactUsScreen(navController = navController)
        }
    }
}