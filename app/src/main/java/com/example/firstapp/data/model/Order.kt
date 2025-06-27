package com.example.firstapp.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("reservation_id")
    val reservationId: Int,

    @SerialName("user_id")
    val userId: String,

    @SerialName("waiter_id")
    val waiterId: String,

    @SerialName("created_at")
    val createdAt: LocalDateTime,

    @SerialName("status")
    val status: OrderStatus
)
