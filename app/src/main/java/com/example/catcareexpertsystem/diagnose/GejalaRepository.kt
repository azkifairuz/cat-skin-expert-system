package com.example.catcareexpertsystem.diagnose

import com.example.catcareexpertsystem.utils.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class GejalaRepository {
    private val supabase = SupabaseClient.client

    suspend fun getData(): List<Gejala> {
        return  supabase
            .from("gejala")
            .select(Columns.ALL)
            .decodeList<Gejala>()
    }
}