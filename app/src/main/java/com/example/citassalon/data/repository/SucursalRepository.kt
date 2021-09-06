package com.example.citassalon.data.repository

import android.util.Log
import com.example.citassalon.data.retrofit.RetrofitInstance
import com.example.citassalon.data.retrofit.WebServices
import com.example.citassalon.models.Sucursal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SucursalRepository {

    val retrofit = RetrofitInstance.getRetrofit()
    val webServices = retrofit.create(WebServices::class.java)
    val call = webServices.getPokemon()

    fun makeRequest() {
        call.enqueue(object : Callback<List<Sucursal>> {

            override fun onFailure(call: Call<List<Sucursal>>, t: Throwable) {
                Log.e("error", "Error: $t")
            }

            override fun onResponse(
                call: Call<List<Sucursal>>,
                response: Response<List<Sucursal>>
            ) {
                if (response.code() == 200) {
                    Log.e("Respuesta", "${response.body()}")
                    val country = response.body()
                    Log.e("Respuesta", "${country?.size}")
                } else {
                    Log.e("Not200", "Error not 200: $response")
                }
            }

        })
    }

}