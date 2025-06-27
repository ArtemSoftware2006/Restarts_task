package com.example.firstapp.domain.repository

import com.example.firstapp.data.model.Reservation
import com.example.firstapp.data.model.Table
import com.example.firstapp.data.model.TableStatus
import java.time.LocalDateTime

/** Интерфейс репозитория для работы с бронированиями и столами */
interface ReservationRepository {
    suspend fun getFreeTables(dateTime: LocalDateTime): List<Table>
    suspend fun createReservation(reservation: Reservation): Reservation
    suspend fun updateTableStatus(tableId: Int, status: TableStatus)
}