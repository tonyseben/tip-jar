package com.example.tipjar.ui.history

import androidx.lifecycle.viewModelScope
import com.example.tipjar.BaseViewModel
import com.example.tipjar.domain.usecase.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val getHistory: GetHistoryUseCase
) :
    BaseViewModel<HistoryDetailContract.State, HistoryDetailContract.Event, HistoryDetailContract.SideEffect>(
        HistoryDetailContract.State()
    ) {

    fun getHistoryDetail(timestamp: Long) {
        viewModelScope.launch {
            val history = getHistory(timestamp)
            setState {
                copy(
                    timestamp = history.timestamp,
                    dateTime = SimpleDateFormat("yyyy MMM d, HH:mm").format(Date(history.timestamp)),
                    currency = history.currency,
                    amount = history.amount.toString(),
                    totalTip = "%.2f".format(history.amount * history.tipPercent / 100),
                    receiptUri = history.receiptUri
                )
            }
        }
    }

    override fun handleEvents(event: HistoryDetailContract.Event) {

    }
}