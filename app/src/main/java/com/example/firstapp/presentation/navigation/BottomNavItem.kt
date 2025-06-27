package com.example.firstapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Reservation : BottomNavItem(
        route = Routes.Reservations,
        icon  = Icons.Default.DateRange,
        label = "Бронь"
    )
    object Dishes : BottomNavItem(
        route = Routes.Dishes,
        icon  = Icons.Default.Menu,
        label = "Меню"
    )
    object Profile : BottomNavItem(
        route = Routes.Profile,
        icon  = Icons.Default.Person,
        label = "Профиль"
    )

    object CreateOrder : BottomNavItem(
        route = Routes.CreateOrder,
        icon = Icons.Default.Edit, // или любой другой подходящий
        label = "Заказ"
    )
    companion object {
        /** Полный набор — потом отфильтруем по роли */
        val allItems = listOf(Reservation, Dishes, Profile, CreateOrder)
    }
}
