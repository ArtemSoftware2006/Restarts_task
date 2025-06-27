package com.example.firstapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dish(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("category")
    val category: String,

    @SerialName("price")
    val price: Float,

    @SerialName("available_count")
    val availableCount: Int,

    @SerialName("active")
    val active: Boolean
)