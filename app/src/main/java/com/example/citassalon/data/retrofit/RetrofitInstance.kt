package com.example.citassalon.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {


        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://192.168.0.157:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}