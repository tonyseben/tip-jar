package com.example.tipjar.ui.home

import androidx.lifecycle.viewModelScope
import com.example.tipjar.BaseViewModel
import com.example.tipjar.domain.usecase.SaveTipUseCase
import com.example.tipjar.domain.usecase.UpdateReceiptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
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

            is HomeContract.Event.OnAmountChange -> onAmountChanged(event.amount)

            is HomeContract.Event.OnPersonCountChange -> onPersonCountChange(event.operator)

            is HomeContract.Event.OnTipPercentChange -> onTipPercentChanged(event.percent)

            is HomeContract.Event.SaveTip -> saveTip()

            is HomeContract.Event.UpdateReceipt -> {
                viewModelScope.launch { /*updateReceipt(event.receipt)*/ }
            }
        }
    }

    private fun onAmountChanged(amount: String) {

        val regex = Pattern.compile("^([1-9][0-9]*|0)?(\\.[0-9]{0,2})?\$")
        if (amount == ".") {
            setState { copy(amount = "0.") }
        } else if (amount.isEmpty() || regex.matcher(amount).matches()) {
            setState { copy(amount = amount) }
        }
        calculateTip()
    }

    private fun onPersonCountChange(operator: Char) {
        setState {
            var per = persons.toInt()
            if (operator == '+' && per < 100) {
                per++
            } else if (operator == '-' && per > 1) {
                per--
            }
            copy(persons = per.toString())
        }
        calculateTip()
    }

    private fun onTipPercentChanged(percent: String) {

        val regex = Pattern.compile("\\b(?:[0-9]?[0-9]|100)\\b")
        if (percent.isEmpty()) {
            setState { copy(tipPercentage = percent) }
        } else if (regex.matcher(percent).matches()) {
            setState { copy(tipPercentage = percent.toInt().toString()) }
        }
        calculateTip()
    }

    private fun calculateTip() {
        setState {
            if (amount.toDoubleOrNull() == null || tipPercentage.toIntOrNull() == null) {
                copy(
                    totalTip = "0.0",
                    perPersonTip = "0.0",
                    isSaveEnabled = false
                )
            } else {
                val total = amount.toDouble() * tipPercentage.toDouble() / 100
                copy(
                    totalTip = "%.2f".format(total),
                    perPersonTip = "%.2f".format(total / persons.toInt()),
                    isSaveEnabled = true
                )
            }
        }
    }

    private fun saveTip() = viewModelScope.launch {
        if (saveTip(state.value.toTipData())) {
            setSideEffect(HomeContract.SideEffect.SaveTipSuccess)
            setState { HomeContract.State() }
        } else {
            setSideEffect(HomeContract.SideEffect.SaveTipFailed)
        }
    }
}