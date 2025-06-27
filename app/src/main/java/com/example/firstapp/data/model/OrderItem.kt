package com.example.firstapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("order_id")
    val orderId: Int,

    @SerialName("dish_id")
    val dishId: Int,

    @SerialName("quantity")
    val quantity: Int
)
