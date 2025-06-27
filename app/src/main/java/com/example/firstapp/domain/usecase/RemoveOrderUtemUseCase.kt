package com.example.firstapp.domain.usecase

import com.example.firstapp.domain.repository.OrderRepository
import jakarta.inject.Inject

class RemoveOrderItemUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(orderItemId: Int) {
        repository.removeOrderItem(orderItemId)
    }
}