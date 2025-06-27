package com.example.firstapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.firstapp.data.model.Dish
import com.example.firstapp.data.model.Order
import com.example.firstapp.data.model.OrderItem
import com.example.firstapp.data.model.OrderStatus
import com.example.firstapp.domain.usecase.AddOrderItemUseCase
import com.example.firstapp.domain.usecase.CreateOrderUseCase
import com.example.firstapp.domain.usecase.GetActiveDishesUseCase
import com.example.firstapp.domain.usecase.GetOrderItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.example.firstapp.data.dto.OrderItemWithDish
import com.example.firstapp.domain.usecase.RemoveOrderItemUseCase
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getDishesUseCase: GetActiveDishesUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val addOrderItemUseCase: AddOrderItemUseCase,
    private val removeOrderItemUseCase: RemoveOrderItemUseCase,
    private val getOrderItemsUseCase: GetOrderItemsUseCase
) : ViewModel() {
    private val _dishes = MutableStateFlow<List<Dish>>(emptyList())
    val dishes: StateFlow<List<Dish>> = _dishes

    private val _order = MutableStateFlow<Order?>(null)
    private val _items = MutableStateFlow<List<OrderItem>>(emptyList())
    val orderItems: StateFlow<List<OrderItem>> = _items

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    fun startOrder(tableId: Int) {
        val now = Clock.System.now()
        val tz = TimeZone.currentSystemDefault()
        val today = now.toLocalDateTime(tz)

        _order.value = Order(
            id = null,
            reservationId = tableId,
            userId = "", // заполнить из session
            waiterId = "", // заполнить из session
            createdAt = today,
            status = OrderStatus.DRAFT
        )
        viewModelScope.launch { _items.value = emptyList() }
    }

    val orderItemsWithDish = MutableStateFlow<List<OrderItemWithDish>>(emptyList())

    private fun updateOrderItemsWithDish() {
        val currentDishes = dishes.value
        val enriched = orderItems.value.mapNotNull { item ->
            val dish = currentDishes.find { it.id == item.dishId }
            dish?.let { OrderItemWithDish(it, item.quantity) }
        }
        orderItemsWithDish.value = enriched
    }

    fun loadDishes() = viewModelScope.launch {
        try {
            _dishes.value = getDishesUseCase()
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    fun addItem(dish: Dish, qty: Int) = viewModelScope.launch {
        _order.value?.let { ord ->
            try {
                val item = addOrderItemUseCase(
                    OrderItem(
                        id = null,
                        orderId = ord.id!!,
                        dishId = dish.id,
                        quantity = qty
                    )
                )
                _items.value = getOrderItemsUseCase(ord.id)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun removeItem(item: OrderItem) = viewModelScope.launch {
        try {
            val itemId = item.id ?: throw IllegalArgumentException("OrderItem.id is null")
            removeOrderItemUseCase(itemId)
            _order.value?.id?.let { orderId ->
                _items.value = getOrderItemsUseCase(orderId)
                updateOrderItemsWithDish()
            }
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    fun submitOrder() = viewModelScope.launch {
        _isSubmitting.value = true
        _order.value?.let { ord ->
            try {
                createOrderUseCase(ord)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
        _isSubmitting.value = false
    }
}
