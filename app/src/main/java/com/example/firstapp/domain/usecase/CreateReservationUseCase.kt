package com.example.firstapp.domain.usecase

import com.example.firstapp.data.model.Reservation
import com.example.firstapp.data.model.TableStatus
import com.example.firstapp.domain.repository.ReservationRepository

class CreateReservationUseCase(
    private val repo: ReservationRepository
) {
    suspend operator fun invoke(reservation: Reservation): Reservation {
        // 1) Создаём бронь
        val created = repo.createReservation(reservation)

        try {
            // 2) Меняем статус стола на RESERVED (или соответствующий)
            repo.updateTableStatus(reservation.tableId, TableStatus.reserved)
        } catch (e: Exception) {
            // 3) Откат: удаляем бронь, если не удалось зарезервировать стол
            //    (или меняем статус брони на CANCELLED, в зависимости от требований)
            // repo.deleteReservation(created.id!!)
            throw e
        }

        return created
    }
}
