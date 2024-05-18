package com.example.tipjar.ui.history

import androidx.lifecycle.viewModelScope
import com.example.tipjar.BaseViewModel
import com.example.tipjar.domain.model.TipData
import com.example.tipjar.domain.usecase.DeleteHistoryUseCase
import com.example.tipjar.domain.usecase.GetHistoryUseCase
import com.example.tipjar.ui.home.HistoryItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistory: GetHistoryUseCase,
    private val deleteHistory: DeleteHistoryUseCase
) :
    BaseViewModel<HistoryContract.State, HistoryContract.Event, HistoryContract.SideEffect>(
        HistoryContract.State()
    ) {

    init {
        viewModelScope.launch {
            val history = getHistory().map { it.toHistoryItem() }
            setState {
                copy(history = history)
            }
        }
    }

    override fun handleEvents(event: HistoryContract.Event) {
        when (event) {
            is HistoryContract.Event.Delete -> deleteItem(event.position)
        }
    }

    private fun deleteItem(position: Int) = viewModelScope.launch {
        val timestamp = state.value.history[position].timestamp
        if (deleteHistory(timestamp)) {
            state.value.history.toMutableList().also { it.removeAt(position) }
            setSideEffect(HistoryContract.SideEffect.DeleteSuccess)
        }
    }

    private fun TipData.toHistoryItem() = HistoryItemUiState(
        timestamp = timestamp,
        dateTime = SimpleDateFormat("yyyy MMM d, HH:mm").format(Date(timestamp)).toString(),
        currency = currency,
        amount = amount.toString(),
        totalTip = "%.2f".format(amount * tipPercent / 100),
        receiptUri = receiptUri
    )

}