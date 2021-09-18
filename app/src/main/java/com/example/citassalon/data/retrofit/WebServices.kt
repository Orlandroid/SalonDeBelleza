package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.Sucursal
import retrofit2.Call
import retrofit2.http.GET

interface WebServices {

    @GET("/estados")
    fun getPokemon(): Call<Sucursal>
}