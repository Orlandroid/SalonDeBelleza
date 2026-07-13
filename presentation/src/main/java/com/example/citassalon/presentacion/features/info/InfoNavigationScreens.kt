package com.example.citassalon.presentacion.features.info

import com.example.data.remote.products.commons.product.ProductSource
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
    data class CategoriesRoute(val source: ProductSource)

    @Serializable
    data class ProductsRoute(val source: ProductSource)

    @Serializable
    data class DetailProductRoute(val source: ProductSource, val productId: Int)

    @Serializable
    data object CartRoute

    @Serializable
    data object OurStaffRoute

    @Serializable
    data object ServicesRoute

    @Serializable
    data object LocationRoute
}
