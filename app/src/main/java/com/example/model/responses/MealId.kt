package com.example.model.responses

import com.google.gson.annotations.SerializedName

data class MealId(
    @SerializedName("meals")
    val details: List<Details>
)