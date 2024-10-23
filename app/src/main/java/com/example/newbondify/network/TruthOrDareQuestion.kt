package com.example.newbondify.network

data class TruthOrDareQuestion(
    val id: String,
    val question: String,
    val rating: String,
    val translations: Translations,
    val type: String
)