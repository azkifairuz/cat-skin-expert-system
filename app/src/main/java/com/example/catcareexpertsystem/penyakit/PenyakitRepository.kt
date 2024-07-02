package com.example.catcareexpertsystem.penyakit

import com.example.catcareexpertsystem.utils.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class PenyakitRepository {
    private val supabase = SupabaseClient.client

    suspend fun getData(): List<Penyakit> {
        return  supabase
            .from("penyakit")
            .select(columns = Columns.ALL)
            .decodeList<Penyakit>()
    }
}