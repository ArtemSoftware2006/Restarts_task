package com.example.firstapp.presentation.screens.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firstapp.presentation.viewmodel.OrderViewModel

@Composable
fun OrderScreen(
    tableId: Int,
    onOrderSubmitted: () -> Unit,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val dishes by viewModel.dishes.collectAsState()
    val items by viewModel.orderItems.collectAsState()
    val error by viewModel.error.collectAsState()
    val submitting by viewModel.isSubmitting.collectAsState()

    LaunchedEffect(tableId) {
        viewModel.startOrder(tableId)
        viewModel.loadDishes()
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Заказ стола #$tableId", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        if (error != null) {
            Text("Ошибка: $error", color = MaterialTheme.colorScheme.error)
        }

        // ✅ Показываем текущие позиции из заказа (не список блюд!)
        LazyColumn(Modifier.weight(1f)) {
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(dishes.find { it.id == item.dishId }?.name ?: "Неизвестное блюдо")
                    Text("x${item.quantity}")
                    IconButton(onClick = { viewModel.removeItem(item) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Удалить")
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        Text("Добавить блюдо:")
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(dishes) { dish ->
                var qty by remember { mutableStateOf(1) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(dish.name)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (qty > 1) qty-- }) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                        Text(qty.toString(), Modifier.width(24.dp), textAlign = TextAlign.Center)
                        IconButton(onClick = { qty++ }) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                        Button(onClick = {
                            viewModel.addItem(dish, qty)
                            qty = 1
                        }) {
                            Text("Добавить")
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.submitOrder()
                onOrderSubmitted()
            },
            enabled = items.isNotEmpty() && !submitting,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (submitting) "Отправка..." else "Оформить заказ")
        }
    }
}
