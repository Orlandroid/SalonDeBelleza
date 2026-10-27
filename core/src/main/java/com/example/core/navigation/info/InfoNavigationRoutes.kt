package com.example.core.navigation.info
import com.example.model.state.models.CategorySource
import com.example.model.state.models.ProductSource
import kotlinx.serialization.Serializable

sealed class InfoNavigationRoutes {
    @Serializable
    data object EstablishingRoute : InfoNavigationRoutes()

    @Serializable
    data object StoresRoute : InfoNavigationRoutes()

    @Serializable
    data object BranchesRoute : InfoNavigationRoutes()

    @Serializable
    data object BranchInfoRoute : InfoNavigationRoutes()

    @Serializable
    data class CategoriesRoute(
        val source: CategorySource
    ) : InfoNavigationRoutes()

    @Serializable
    data class ProductsRoute(
        val source: ProductSource,
        val category: String? = null
    ) : InfoNavigationRoutes()

    @Serializable
    data class DetailProductRoute(
        val source: ProductSource,
        val productId: Int
    ) : InfoNavigationRoutes()

    @Serializable
    data object CartRoute : InfoNavigationRoutes()

    @Serializable
    data object OurStaffRoute : InfoNavigationRoutes()

    @Serializable
    data object ServicesRoute : InfoNavigationRoutes()

    @Serializable
    data object SuccessScreenRoute : InfoNavigationRoutes()

    @Serializable
    data object LocationRoute : InfoNavigationRoutes()
}
