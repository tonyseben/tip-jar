package com.example.tipjar.ui.home

import androidx.lifecycle.viewModelScope
import com.example.tipjar.BaseViewModel
import com.example.tipjar.domain.usecase.SaveTipUseCase
import com.example.tipjar.domain.usecase.UpdateReceiptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val saveTip: SaveTipUseCase,
    private val updateReceipt: UpdateReceiptUseCase
) :
    BaseViewModel<HomeContract.State, HomeContract.Event, HomeContract.SideEffect>(
        HomeContract.State()
    ) {

    init {
        calculateTip()
    }

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {

            is HomeContract.Event.OnAmountChange -> {
                setState { copy(amount = event.amount) }
                calculateTip()
            }

            is HomeContract.Event.OnPersonCountChange -> {
                setState {
                    val count =
                        if (event.operator == '+') persons.toInt() + 1 else persons.toInt() - 1
                    copy(persons = count.toString())
                }
                calculateTip()
            }

            is HomeContract.Event.OnTipPercentChange -> {
                setState { copy(tipPercentage = event.percent) }
                calculateTip()
            }

            is HomeContract.Event.SaveTip -> {
                viewModelScope.launch { saveTip(state.value.toTipData()) }
            }

            is HomeContract.Event.UpdateReceipt -> {
                viewModelScope.launch { /*updateReceipt(event.receipt)*/ }
            }
        }
    }

    private fun calculateTip() {
        setState {
            if (amount.isEmpty() || tipPercentage.isEmpty()) {
                copy(
                    totalTip = "0.0",
                    perPersonTip = "0.0"
                )
            } else {
                val total = amount.toDouble() * tipPercentage.toDouble() / 100
                copy(
                    totalTip = total.toString(),
                    perPersonTip = (total / persons.toDouble()).toString()
                )
            }
        }
    }
}