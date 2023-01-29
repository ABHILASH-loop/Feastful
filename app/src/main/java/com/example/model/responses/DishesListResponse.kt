package com.example.model.responses

import com.google.gson.annotations.SerializedName

data class DishesListResponse(
    @SerializedName("meals")
    val mealsResponse: List<MealsResponse>
)