package com.example.firstapp.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientInstance {
    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://wlwoqimlacjxkvhvlibe.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Indsd29xaW1sYWNqeGt2aHZsaWJlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTA1MjQxOTcsImV4cCI6MjA2NjEwMDE5N30.HlwanMyLVirkdX54gk0oZtOYmuF25YcLfudcc9Jexr8"
    ) {
        install(Auth)
        install(Postgrest)
    }
}