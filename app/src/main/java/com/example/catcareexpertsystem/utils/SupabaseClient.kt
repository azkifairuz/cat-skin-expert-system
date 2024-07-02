package com.example.catcareexpertsystem.utils

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    private const val SUPABASE_URL = "https://zrigpkyppmnrzpghumko.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InpyaWdwa3lwcG1ucnpwZ2h1bWtvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTkzOTY0NTUsImV4cCI6MjAzNDk3MjQ1NX0.VGOuPxJRDapQW5NflJJ7aTeBKAR-ETFPdmkIv7sqIuE"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
    }
}