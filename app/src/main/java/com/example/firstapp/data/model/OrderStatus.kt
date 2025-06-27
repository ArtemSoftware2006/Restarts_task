package com.example.firstapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatus {
    @SerialName("draft")
    DRAFT,

    @SerialName("submitted")
    SUBMITTED,

    @SerialName("in_progress")
    IN_PROGRESS,

    @SerialName("completed")
    COMPLETED,

    @SerialName("cancelled")
    CANCELLED
}