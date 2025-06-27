package com.example.firstapp.domain.repository

import com.example.firstapp.data.model.Order
import com.example.firstapp.data.model.OrderItem

interface OrderRepository {
    /** Создаёт новый заказ и возвращает его */
    suspend fun createOrder(order: Order): Order

    /** Добавляет позицию в существующий заказ */
    suspend fun addOrderItem(item: OrderItem): OrderItem

    /** Возвращает список позиций для заказа */
    suspend fun getOrderItems(orderId: Int): List<OrderItem>

    /** Обновляет статус заказа */
    suspend fun updateOrderStatus(orderId: Int, status: String)

    suspend fun removeOrderItem(orderItemId: Int)
}