package com.example.tipjar.ui.history

import com.example.tipjar.UiEvent
import com.example.tipjar.UiSideEffect
import com.example.tipjar.UiState
import com.example.tipjar.ui.home.HistoryItemUiState

class HistoryContract {
    data class State(
        val history: List<HistoryItemUiState> = emptyList()
    ) : UiState

    sealed class Event : UiEvent {
        data class OnItemClick(val item: HistoryItemUiState) : Event()
    }

    object SideEffect : UiSideEffect
}