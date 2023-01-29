package com.example.model.api
import com.example.model.responses.DishesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {
    @GET("/")
    suspend fun getMeals(): DishesListResponse
}