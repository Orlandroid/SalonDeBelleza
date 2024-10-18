package com.example.citassalon.presentacion.features.info

import com.example.domain.entities.remote.Product
import kotlinx.serialization.Serializable

sealed class InfoNavigationScreens {
    @Serializable
    data object EstablishingRoute

    @Serializable
    data object StoresRoute

    @Serializable
    data object BranchesRoute

    @Serializable
    data object BranchInfoRoute

    @Serializable
    data object CategoriesRoute

    @Serializable
    data class ProductsRoute(val category: String)

    @Serializable
    data class DetailProductRoute(val product: Product)

    @Serializable
    data object CartRoute

    @Serializable
    data object OurStaffRoute

    @Serializable
    data object ServicesRoute

    @Serializable
    data object LocationRoute
}

