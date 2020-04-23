package com.example.isvirin.skbktestapp.domain.model


data class Contact(
    val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: Temper,
    val educationPeriod: Education
)

enum class Temper {melancholic, phlegmatic, sanguine, choleric}
//enum class Temper {MELANCHOLIC, PHLEGMATIC, SANGUINE, CHOLERIC}

data class Education (
    val start: String,
    var end: String
)