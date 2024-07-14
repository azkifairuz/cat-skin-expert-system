package com.example.goatexpertsystem.diagnose

import com.example.goatexpertsystem.utils.SupabaseClient
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

    suspend fun getGejalaPenyakit(): List<GejalaPenyakit> {
        return supabase
            .from("penyakit_gejala")
            .select(Columns.ALL)
            .decodeList<GejalaPenyakit>()
    }

    suspend fun getSolution(): List<Solusi> {
        return  supabase
            .from("solusi")
            .select(Columns.ALL)
            .decodeList<Solusi>()
    }
}