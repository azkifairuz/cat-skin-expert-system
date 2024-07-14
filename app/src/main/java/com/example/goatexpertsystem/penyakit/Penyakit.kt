package com.example.goatexpertsystem.penyakit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Penyakit(
    @SerialName("penyakit_id")
    val penyakitId: Int,
    @SerialName("penyakit_code")
    val penyakitCode: String,
    @SerialName("penyakit_name")
    val penyakit: String,
    @SerialName("deskripsi")
    val deskripsi: String
)
