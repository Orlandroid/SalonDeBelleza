package com.example.citassalon.data.models.rickandmorty

data class LocationResponse(
    val info: Info,
    val results: List<ResultsLocations>
)

data class ResultsLocations(
    val id:Int,
    val name:String,
    val type:String,
    val dimension:String,
    val residents:List<String>,
    val url:String,
    val created:String
)


