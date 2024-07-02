package com.example.catcareexpertsystem.diagnose

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Answer(
    val penyakitCode: String,
    val cf:Double
)