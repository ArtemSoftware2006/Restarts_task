package com.example.firstapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.firstapp.presentation.screens.dishes.DishesScreen
import com.example.firstapp.presentation.screens.main.MainScreen
import com.example.firstapp.presentation.screens.signin.SignInScreen
import com.example.firstapp.presentation.screens.signup.SignUpScreen
import com.example.firstapp.presentation.screens.splash.SplashScreen
import com.example.firstapp.presentation.screens.reservation.ReservationScreen
import com.example.firstapp.presentation.screens.profile.ProfileScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    // Храним роль пользователя
    var role by remember { mutableStateOf("guest") }

    // Пункты нижнего меню по ролям
    val bottomItems = when (role) {
        "guest", "client" -> listOf(
            BottomNavItem.Dishes,
            BottomNavItem.Reservation,
        )
        "waiter" -> listOf(
            BottomNavItem.Dishes,
            BottomNavItem.Reservation,
            BottomNavItem.CreateOrder,
            BottomNavItem.Profile
        )
        "admin" -> BottomNavItem.allItems
        else -> listOf(BottomNavItem.Dishes)
    }

    Scaffold(
        bottomBar = {
            val navBackStack by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStack?.destination?.route
            // Список маршрутов, где показываем меню
            val menuRoutes = bottomItems.map { it.route } + Routes.MainWithRole
            if (currentRoute in menuRoutes) {
                NavigationBar {
                    bottomItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Splash,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Splash
            composable(Routes.Splash) {
                SplashScreen(navController)
            }

            // SignIn
            composable(Routes.SignIn) {
                SignInScreen(
                    onSignInSuccess = { newRole ->
                        role = newRole
                        // Переходим к главному экрану
                        navController.navigate(Routes.mainWithRole(newRole)) {
                            popUpTo(Routes.Splash) { inclusive = true }
                        }
                    },
                    onNavigateToSignUp = {
                        navController.navigate(Routes.SignUp) {
                            popUpTo(Routes.SignIn) { inclusive = true }
                        }
                    }
                )
            }

            // SignUp
            composable(Routes.SignUp) {
                SignUpScreen(
                    onSignUpSuccess = {
                        navController.navigate(Routes.SignIn) {
                            popUpTo(Routes.SignUp) { inclusive = true }
                        }
                    },
                    onNavigateToSignIn = {
                        navController.navigate(Routes.SignIn) {
                            popUpTo(Routes.SignUp) { inclusive = true }
                        }
                    }
                )
            }

            // MainWithRole
            composable(
                route = Routes.MainWithRole,
                arguments = listOf(navArgument("role") { type = NavType.StringType })
            ) { backStackEntry ->
                val newRole = backStackEntry.arguments?.getString("role") ?: "guest"
                LaunchedEffect(newRole) { role = newRole }
                MainScreen(
                    role = newRole,
                    onLogout = {
                        role = "guest"
                        navController.navigate(Routes.SignIn) {
                            popUpTo(Routes.MainWithRole) { inclusive = true }
                        }
                    }
                )
            }

            // Reservation
            composable(Routes.Reservations) {
                ReservationScreen(
                    onBookingSuccess = {
                        // После бронирования можно остаться или показать уведомление
                    }
                )
            }

            // Dishes
            composable(Routes.Dishes) {
                DishesScreen()
            }

            // Profile
            composable(Routes.Profile) {
                ProfileScreen(
                    onLogout = {
                        role = "guest"
                        navController.navigate(Routes.SignIn) {
                            popUpTo(Routes.Profile) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.CreateOrder) {
                // Пока можно заглушку:
                TODO()
                Text("Экран создания заказа")
            }
        }
    }
}
