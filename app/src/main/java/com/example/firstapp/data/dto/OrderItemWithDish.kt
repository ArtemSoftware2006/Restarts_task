package com.example.firstapp.data.dto

import com.example.firstapp.data.model.Dish

data class OrderItemWithDish(
    val dish: Dish,
    val quantity: Int
)