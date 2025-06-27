package com.example.firstapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.firstapp.data.model.Dish
import com.example.firstapp.domain.usecase.GetActiveDishesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch

@HiltViewModel
class DishesViewModel @Inject constructor(
    private val getActiveDishes: GetActiveDishesUseCase
) : ViewModel() {
    private val _dishes = MutableStateFlow<List<Dish>>(emptyList())
    val dishes:  StateFlow<List<Dish>> = _dishes

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadDishes() = viewModelScope.launch {
        try {
            _dishes.value = getActiveDishes()
            _error.value = null
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
}
