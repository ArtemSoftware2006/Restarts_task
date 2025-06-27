package com.example.firstapp.data.repository

import com.example.firstapp.data.SupabaseClientInstance
import com.example.firstapp.data.model.Reservation
import com.example.firstapp.data.model.Table
import com.example.firstapp.data.model.TableStatus
import com.example.firstapp.domain.repository.ReservationRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.selects.select
import java.time.LocalDateTime

/** Реализация ReservationRepository на основе Supabase Postgrest */
class SupabaseReservationRepository(
    private val supabase: SupabaseClient = SupabaseClientInstance.client
) : ReservationRepository {

    override suspend fun getFreeTables(date: LocalDateTime): List<Table> {
        // Пример: возвращаем все столы со статусом FREE
        return supabase
            .from("tables")
            .select{
                filter {
                    eq("status", "free")
                }
            }
            .decodeList<Table>()             // Декодируем в List<Table>
    }

    override suspend fun createReservation(reservation: Reservation): Reservation {
        // Вставляем запись и возвращаем созданную сущность
        return supabase
            .from("reservations")
            .insert(reservation, {
                select()
            })              // INSERT INTO reservations ...
            .decodeSingle<Reservation>()     // Декодируем единственный объект
    }

    override suspend fun updateTableStatus(tableId: Int, status: TableStatus) {
         supabase
            .from("tables")
            .update(mapOf("status" to status.name.lowercase())) {
                filter {
                    eq("id", tableId)
                }
            }

    }
}