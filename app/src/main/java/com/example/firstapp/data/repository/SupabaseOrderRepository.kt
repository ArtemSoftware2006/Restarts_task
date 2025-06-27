package com.example.firstapp.data.repository

import com.example.firstapp.data.SupabaseClientInstance
import com.example.firstapp.data.model.Order
import com.example.firstapp.data.model.OrderItem
import com.example.firstapp.domain.repository.OrderRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from


class SupabaseOrderRepository(
    private val supabase: SupabaseClient = SupabaseClientInstance.client
) : OrderRepository {
    override suspend fun createOrder(order: Order): Order {
        return supabase
            .from("orders")
            .insert(order) {
                select()
            }
            .decodeSingle<Order>()
    }

    override suspend fun addOrderItem(item: OrderItem): OrderItem {
        return supabase
            .from("order_items")
            .insert(item) {
                select()
            }
            .decodeSingle<OrderItem>()
    }

    override suspend fun getOrderItems(orderId: Int): List<OrderItem> {
        return supabase
            .from("order_items")
            .select() {
                filter {
                    eq("order_id", orderId)
                }
            }
            .decodeList<OrderItem>()
    }

    override suspend fun updateOrderStatus(orderId: Int, status: String) {
        supabase
            .from("orders")
            .update(mapOf("status" to status)) {
                filter {
                    eq("id", orderId)
                }
                select()
            }
            .decodeSingle<Order>()
    }

    override suspend fun removeOrderItem(orderItemId: Int) {
        supabase
            .from("order_item")
            .delete {
                filter {
                    eq("id", orderItemId)
                }
            }
    }
}