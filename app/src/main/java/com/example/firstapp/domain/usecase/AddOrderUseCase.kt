package com.example.firstapp.domain.usecase

import com.example.firstapp.data.model.OrderItem
import com.example.firstapp.domain.repository.OrderRepository

class AddOrderItemUseCase(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(item: OrderItem): OrderItem =
        repository.addOrderItem(item)
}