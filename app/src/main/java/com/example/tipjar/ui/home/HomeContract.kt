package com.example.tipjar.ui.home

import com.example.tipjar.UiEvent
import com.example.tipjar.UiSideEffect
import com.example.tipjar.UiState
import com.example.tipjar.domain.model.TipData

class HomeContract {
    data class State(
        val currency: String = "$",
        val amount: String = "",
        val persons: String = "1",
        val tipPercentage: String = "",
        val totalTip: String = "0.0",
        val perPersonTip: String = "0.0",
        val isReceipt: Boolean = false,
        val isSaveEnabled: Boolean = false
    ) : UiState

    sealed class Event : UiEvent {
        data class OnAmountChange(val amount: String) : Event()
        data class OnPersonCountChange(val operator: Char) : Event()
        data class OnTipPercentChange(val percent: String) : Event()
        data object OnReceiptChange : Event()
        data object SaveTip : Event()
        data object UpdateReceipt : Event()
        data object ChangeCurrency : Event()
    }

    sealed class SideEffect : UiSideEffect {
        data class CaptureReceipt(val timestamp: Long) : SideEffect()
        data object SaveTipSuccess : SideEffect()
        data object SaveTipFailed : SideEffect()
    }
}

fun HomeContract.State.toTipData() = TipData(
    timestamp = System.currentTimeMillis(),
    currency = currency,
    amount = amount.toDouble(),
    pax = persons.toInt(),
    tipPercent = tipPercentage.toInt(),
    receiptUri = null
)