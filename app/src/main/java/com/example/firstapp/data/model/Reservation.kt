package com.example.firstapp.data.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName


@Serializable
enum class ReservationStatus {
    @SerialName("pending")
    PENDING,

    @SerialName("confirmed")
    CONFIRMED,

    @SerialName("cancelled")
    CANCELLED,

    @SerialName("expired")
    EXPIRED,

    @SerialName("completed")
    COMPLETED,

    @SerialName("no_show")
    NO_SHOW,
}
@Serializable
data class Reservation(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("table_id")
    val tableId: Int,

    @SerialName("user_id")
    val userId: String,

    @SerialName("datetime_start")
    val dateTimeStart: LocalDateTime,

    @SerialName("datetime_end")
    val dateTimeEnd: LocalDateTime,

    @SerialName("status")
    val status: ReservationStatus,
)