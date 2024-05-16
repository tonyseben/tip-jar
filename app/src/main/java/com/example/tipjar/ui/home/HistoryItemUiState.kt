package com.example.tipjar.ui.home

data class HistoryItemUiState(
    val dateTime: String,
    val currency: String,
    val amount: String,
    val pax: String,
    val tipPercent: String,
    val totalTip: String,
    val perPersonTip: String,
    val receiptUri: String?
)
