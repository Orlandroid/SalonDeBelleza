package com.example.citassalon.data.models

data class Products(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category:String,
    val image:String,
    val rating: Rating
){
    data class Rating(
        val rate:Double,
        val count:Int
    )
}

