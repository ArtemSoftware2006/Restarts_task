package com.example.firstapp.domain.usecase

import com.example.firstapp.domain.repository.DishRepository

class GetActiveDishesUseCase(private val repo: DishRepository) {
    suspend operator fun invoke() = repo.getAllActiveDishes()
}