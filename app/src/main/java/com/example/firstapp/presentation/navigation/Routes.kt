package com.example.firstapp.presentation.navigation

// presentation/navigation/Routes.kt
object Routes {
    const val Splash        = "splash"
    const val SignIn        = "signin"
    const val SignUp        = "signup"
    const val MainWithRole  = "main/{role}"
    const val Reservations   = "reservation"
    const val CreateOrder = "create_order"
    const val Dishes = "dishes"
    const val Profile       = "profile"

    fun   mainWithRole(role: String) = "main/$role"
}
