package com.example.core.navigation.info
import com.example.domain.CategorySource
import com.example.domain.ProductSource
import kotlinx.serialization.Serializable

sealed class InfoNavigationScreens {
    @Serializable
    data object EstablishingRoute : InfoNavigationScreens()

    @Serializable
    data object StoresRoute : InfoNavigationScreens()

    @Serializable
    data object BranchesRoute : InfoNavigationScreens()

    @Serializable
    data object BranchInfoRoute : InfoNavigationScreens()

    @Serializable
    data class CategoriesRoute(val source: CategorySource) : InfoNavigationScreens()

    @Serializable
    data class ProductsRoute(val source: ProductSource, val category: String? = null) :
        InfoNavigationScreens()

    @Serializable
    data class DetailProductRoute(val source: ProductSource, val productId: Int) :
        InfoNavigationScreens()

    @Serializable
    data object CartRoute : InfoNavigationScreens()

    @Serializable
    data object OurStaffRoute : InfoNavigationScreens()

    @Serializable
    data object ServicesRoute : InfoNavigationScreens()


    @Serializable
    data object SuccessScreenRoute : InfoNavigationScreens()

    @Serializable
    data object LocationRoute : InfoNavigationScreens()
}
