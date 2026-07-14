package com.example.domain.entities.remote.products

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double? = null,
    val image: String? = null,
    val category: String? = null
) {

    companion object {
        fun dummyProduct() = Product(
            id = 1,
            title = "Dummy Product",
            description = "This is a dummy product for testing purposes.",
            price = 9.99,
            rating = 4.5,
            image = "https://via.placeholder.com/150"
        )
    }
}
