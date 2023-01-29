package com.example.feastful.ui.meals

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.MealsRepository
import com.example.model.responses.MealsResponse
import kotlinx.coroutines.launch
import java.util.*

class MealsCategoriesViewModel(
    private val repository: MealsRepository = MealsRepository()
) : ViewModel() {
    val meals: MutableState<List<MealsResponse>> = mutableStateOf(emptyList())


    init {
        viewModelScope.launch {
            val mealsList = getMeals()
            meals.value = mealsList
        }
    }

    private suspend fun getMeals(): List<MealsResponse> {
        return repository.getMeals().mealsResponse
    }

}