package com.example.firstapp.domain.usecase

import com.example.firstapp.data.model.Table
import com.example.firstapp.domain.repository.ReservationRepository
import java.time.LocalDateTime

class GetFreeTablesUseCase(
    private val repo: ReservationRepository
) {
    suspend operator fun invoke(): List<Table> =
        repo.getFreeTables(dateTime = LocalDateTime.now())
}
