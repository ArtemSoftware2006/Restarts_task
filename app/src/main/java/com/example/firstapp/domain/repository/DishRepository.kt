package com.example.firstapp.domain.repository

import com.example.firstapp.data.model.Dish

interface DishRepository {
    suspend fun getAllActiveDishes(): List<Dish>
}