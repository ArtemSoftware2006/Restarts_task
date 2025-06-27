package com.example.firstapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstapp.data.model.Reservation
import com.example.firstapp.data.model.Table
import com.example.firstapp.domain.usecase.CreateReservationUseCase
import com.example.firstapp.domain.usecase.GetFreeTablesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

/** ViewModel для экрана бронирования */
@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val getFreeTablesUseCase: GetFreeTablesUseCase,
    private val createReservationUseCase: CreateReservationUseCase
) : ViewModel() {

    private val _tables = MutableStateFlow<List<Table>>(emptyList())
    val tables: StateFlow<List<Table>> = _tables

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _bookingSuccess = MutableStateFlow<Reservation?>(null)
    val bookingSuccess: StateFlow<Reservation?> = _bookingSuccess

    /** Загружает список свободных столов */
    fun loadFreeTables() {
        viewModelScope.launch {
            try {
                _tables.value = getFreeTablesUseCase()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    /** Создаёт бронь */
    fun bookTable(reservation: Reservation) {
        viewModelScope.launch {
            try {
                _bookingSuccess.value = createReservationUseCase(reservation)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
