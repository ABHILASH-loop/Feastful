package com.example.model.api

import com.example.model.responses.MealId
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsId {
    @GET("lookup.php")
    suspend fun getInstruction(@Query("i") i: Int): MealId
}