package com.example.tipjar.ui.history

import com.example.tipjar.UiEvent
import com.example.tipjar.UiSideEffect
import com.example.tipjar.UiState

class HistoryDetailContract {
    data class State(
        val timestamp: Long = 0L,
        val dateTime: String = "",
        val currency: String = "",
        val amount: String = "",
        val totalTip: String = "",
        val receiptUri: String? = ""
    ) : UiState

    sealed class Event : UiEvent

    object SideEffect : UiSideEffect
}