package com.example.firstapp.domain.usecase

import com.example.firstapp.data.model.Order
import com.example.firstapp.domain.repository.OrderRepository

class CreateOrderUseCase(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(order: Order): Order =
        repository.createOrder(order)
}