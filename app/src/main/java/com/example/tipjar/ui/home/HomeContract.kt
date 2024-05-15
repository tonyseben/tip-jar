package com.example.tipjar.ui.home

import com.example.tipjar.UiEvent
import com.example.tipjar.UiSideEffect
import com.example.tipjar.UiState
import com.example.tipjar.domain.model.TipData

class HomeContract {
    data class State(
        val currency: String = "USD",
        val amount: String = "100.0",
        val persons: String = "1",
        val tipPercentage: String = "10",
        val totalTip: String = "0.0",
        val perPersonTip: String = "0.0",
        val isReceipt: Boolean = false,
        val isSaveEnabled: Boolean = false
    ) : UiState

    sealed class Event : UiEvent {
        data class OnAmountChange(val amount: String) : Event()
        data class OnPersonCountChange(val operator: Char) : Event()
        data class OnTipPercentChange(val percent: String) : Event()
        data object SaveTip : Event()
        data object UpdateReceipt : Event()
    }

    object SideEffect : UiSideEffect
}

fun HomeContract.State.toTipData() = TipData(
    timestamp = System.currentTimeMillis(),
    currency = currency,
    amount = amount.toDouble(),
    pax = persons.toInt(),
    tipPercent = tipPercentage.toInt(),
    receiptUri = null
)