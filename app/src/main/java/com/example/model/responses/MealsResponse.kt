package com.example.model.responses

import com.google.gson.annotations.SerializedName

data class MealsResponse(
    @SerializedName("idMeal")
    val id: String,
    @SerializedName("strMeal")
    val name: String,
    @SerializedName("strMealThumb")
    val imageUrl: String,
    @SerializedName("strDesc")
    val description: String
)