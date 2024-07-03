package com.example.catcareexpertsystem.diagnose

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Answer(
    val gejalaCode: String,
    val cf:Double
)

@Serializable
data class Result(
    val penyakit: String,
    val cf: Double
)
@Serializable
data class Gejala(
    @SerialName("gejala_code")
    val gejalaCode: String,
    @SerialName("gejala_name")
    val gejalaName: String
)

@Serializable
data class GejalaPenyakit(
    @SerialName("gejala_code")
    val gejalaCode: String,
    @SerialName("penyakit_code")
    val penyakitCode: String,
    @SerialName("certainty_factor")
    val nilaiCf: Double,
)

