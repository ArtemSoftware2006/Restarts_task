package com.example.firstapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient
) : ViewModel() {

    // Флоу, по которому экран подпишется и узнает, что нужно уйти на экран входа
    private val _signedOut = MutableSharedFlow<Unit>(replay = 0)
    val signedOut = _signedOut.asSharedFlow()

    fun signOut() {
        viewModelScope.launch {
            // Разлогиниваемся в Supabase
            supabaseClient.auth.signOut()
            // Шлём событие в UI
            _signedOut.emit(Unit)
        }
    }
}