package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.rickandmorty.CharacterResponse
import com.example.citassalon.data.models.rickandmorty.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: String): CharacterResponse

    @GET("location")
    suspend fun getLocations(@Query("page") page: String): LocationResponse


}