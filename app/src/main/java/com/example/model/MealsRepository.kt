package com.example.model

import android.util.Log
import com.example.model.api.MealsId
import com.example.model.api.RetrofitMealidInstatnce
import com.example.model.api.RetrofitMealsInstance
import com.example.model.responses.Details
import com.example.model.responses.DishesListResponse
import com.example.model.responses.MealId

class MealsRepository(
    private val retrofitMealsInstance: RetrofitMealsInstance = RetrofitMealsInstance,
    private val retrofitMealIdInstance: RetrofitMealidInstatnce = RetrofitMealidInstatnce
) {

    suspend fun getMeals(): DishesListResponse {
        return retrofitMealsInstance.api.getMeals()
    }

    suspend fun getInstructions(id: Int): List<Details> {
        return retrofitMealIdInstance.api.getInstruction(id).details
    }

}