package com.example.firstapp.domain.usecase

import com.example.firstapp.data.model.OrderItem
import com.example.firstapp.domain.repository.OrderRepository

class GetOrderItemsUseCase(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(orderId: Int): List<OrderItem> =
        repository.getOrderItems(orderId)
}