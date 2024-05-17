package com.example.tipjar.ui.history

import androidx.lifecycle.viewModelScope
import com.example.tipjar.BaseViewModel
import com.example.tipjar.domain.model.TipData
import com.example.tipjar.domain.usecase.GetHistoryUseCase
import com.example.tipjar.ui.home.HistoryItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistory: GetHistoryUseCase
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

    }

    private fun TipData.toHistoryItem() = HistoryItemUiState(
        dateTime = SimpleDateFormat("yyyy MMM d, HH:mm").format(Date(timestamp)).toString(),
        currency = currency,
        amount = amount.toString(),
        pax = pax.toString(),
        tipPercent = tipPercent.toString(),
        totalTip = "%.2f".format(amount * tipPercent / 100),
        perPersonTip = "%.2f".format(amount * tipPercent / 100 / pax),
        receiptUri = receiptUri,
    )

}