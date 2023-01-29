package com.example.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitMealsInstance {
    val api: MealsApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://myprojapi.000webhostapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealsApi::class.java)
    }
}