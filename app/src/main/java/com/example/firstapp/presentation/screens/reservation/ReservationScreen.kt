package com.example.firstapp.presentation.screens.reservation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstapp.data.model.Reservation
import com.example.firstapp.data.model.Table
import com.example.firstapp.presentation.viewmodel.ReservationViewModel
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firstapp.data.SupabaseClientInstance
import com.example.firstapp.data.model.ReservationStatus
import io.github.jan.supabase.auth.auth
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@Composable
fun ReservationScreen(
    viewModel: ReservationViewModel = hiltViewModel(),
    onBookingSuccess: (Reservation) -> Unit
) {
    val tables by viewModel.tables.collectAsState()
    val error by viewModel.error.collectAsState()
    val booking by viewModel.bookingSuccess.collectAsState()
    var selectedTable by remember { mutableStateOf<Table?>(null) }
    var dateTime by remember { mutableStateOf(Clock.System
        .now()  // Instant
        .toLocalDateTime(TimeZone.currentSystemDefault())) }

    LaunchedEffect(Unit) {
        viewModel.loadFreeTables()
    }

    LaunchedEffect(booking) {
        booking?.let {
            onBookingSuccess(it)
            viewModel.loadFreeTables()
            selectedTable = null
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "Свободные столы",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (error != null) {
            Text("Ошибка: $error", color = MaterialTheme.colorScheme.error)
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(tables) { table ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedTable = table },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Стол #${table.id} (мест: ${table.seats})")
                        if (selectedTable == table) {
                            Spacer(Modifier.weight(1f))
                            Text("Выбран", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }

        val tz = TimeZone.currentSystemDefault()
        Button(onClick = {
            val userUuid = SupabaseClientInstance
                .client
                .auth
                .currentUserOrNull()
                ?.id
                ?: return@Button  // или показать ошибку, если вдруг user == null

            selectedTable?.let { table ->
                val reservation = Reservation(
                    tableId       = table.id,
                    userId        = userUuid,       // теперь строка UUID
                    dateTimeStart = dateTime,
                    dateTimeEnd   = dateTime
                        .toInstant(tz)
                        .plus(1, DateTimeUnit.HOUR, tz)
                        .toLocalDateTime(tz),
                    status = ReservationStatus.PENDING
                )
                viewModel.bookTable(reservation)
            }
        }, enabled = selectedTable != null) {
            Text("Забронировать выбранный стол")
        }
    }
}
