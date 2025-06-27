package com.example.firstapp.domain.usecase

import com.example.firstapp.domain.repository.OrderRepository

class UpdateOrderStatusUseCase(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(orderId: Int, status: String) {
        repository.updateOrderStatus(orderId, status)
    }
}