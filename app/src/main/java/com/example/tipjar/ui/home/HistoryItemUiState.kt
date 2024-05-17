package com.example.tipjar.ui.home

data class HistoryItemUiState(
    val timestamp: Long,
    val dateTime: String,
    val currency: String,
    val amount: String,
    val totalTip: String,
    val receiptUri: String?
)
