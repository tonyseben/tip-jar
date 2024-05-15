package com.example.tipjar.domain.model

data class TipData(
    val timestamp: Long,
    val currency: String,
    val amount: Double,
    val pax: Int,
    val tipPercent: Int,
    val receiptUri: String?
)
