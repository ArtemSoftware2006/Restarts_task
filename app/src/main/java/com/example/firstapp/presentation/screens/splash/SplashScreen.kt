package com.example.firstapp.presentation.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.firstapp.data.SupabaseClientInstance
import com.example.firstapp.presentation.navigation.Routes
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        modifier         = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

    LaunchedEffect(Unit) {
        // Короткая задержка, чтобы не мелькнуло слишком резко
        delay(500)
        val session = SupabaseClientInstance.client.auth.currentSessionOrNull()
        if (session != null) {
            // Если есть сессия, читаем роль из user_metadata
            val role = SupabaseClientInstance.client.auth.currentUserOrNull()
                ?.userMetadata
                ?.get("role") as? String
                ?: "client"
            navController.navigate(Routes.mainWithRole(role)) {
                popUpTo(Routes.Splash) { inclusive = true }
            }
        } else {
            // Иначе — экран входа
            navController.navigate(Routes.SignIn) {
                popUpTo(Routes.Splash) { inclusive = true }
            }
        }
    }
}