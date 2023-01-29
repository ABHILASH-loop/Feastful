package com.example.feastful.ui.mealsDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.MealsRepository
import com.example.model.responses.Details
import com.example.model.responses.MealsResponse
import kotlinx.coroutines.launch

class DetailsScreenViewModel
    (
    private val repository: MealsRepository = MealsRepository(),
) : ViewModel() {
    val instruction: MutableState<List<Details>> = mutableStateOf(emptyList())

    fun getInstruction(id: Int) {
        viewModelScope.launch {
            instruction.value = repository.getInstructions(id)
        }
    }
}