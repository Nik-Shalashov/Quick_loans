package ru.android.shift_education.domain.entities

data class LoanConditionsEntity(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
)
