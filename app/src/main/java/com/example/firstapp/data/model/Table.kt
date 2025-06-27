package com.example.firstapp.data.model

import kotlinx.serialization.Serializable

/** Статус стола: свободен или занят */
@Serializable
enum class TableStatus {
    free,
    reserved,
    occupied
}
/** Модель стола */
@Serializable
data class Table(
    val id: Int,
    val seats: Int,
    val status: TableStatus
)