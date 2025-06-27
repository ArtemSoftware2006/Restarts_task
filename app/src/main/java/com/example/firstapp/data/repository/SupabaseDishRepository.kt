package com.example.firstapp.data.repository

import com.example.firstapp.data.SupabaseClientInstance
import com.example.firstapp.data.model.Dish
import com.example.firstapp.domain.repository.DishRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class SupabaseDishRepository(
    private val supabase: SupabaseClient = SupabaseClientInstance.client
) : DishRepository {
    override suspend fun getAllActiveDishes(): List<Dish> =
         supabase
            .from("dishes")
            .select{
                filter {
                    eq("active", true)
                }
            }
            .decodeList<Dish>()
}