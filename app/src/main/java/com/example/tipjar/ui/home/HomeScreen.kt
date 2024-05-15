package com.example.tipjar.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tipjar.ui.home.component.NumberPicker
import com.example.tipjar.ui.home.component.TJButton
import com.example.tipjar.ui.home.component.TJCheckBox
import com.example.tipjar.ui.home.component.TJTextField

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    Column {

        TJTextField(
            label = "Enter amount",
            value = state.value.amount,
            onChange = {
                viewModel.setEvent(HomeContract.Event.OnAmountChange(it))
            }
        )

        NumberPicker(
            label = "How many people?",
            start = state.value.persons,
            onClick = {
                viewModel.setEvent(HomeContract.Event.OnPersonCountChange(it))
            }
        )

        TJTextField(
            label = "% TIP",
            value = state.value.tipPercentage,
            onChange = {
                viewModel.setEvent(HomeContract.Event.OnTipPercentChange(it))
            }
        )

        Row {
            Text(text = "Total Tip")
            Text(text = state.value.totalTip)
        }

        Row {
            Text(text = "Per Person")
            Text(text = state.value.perPersonTip)
        }

        TJCheckBox(label = "Take photo of receipt")
        TJButton(
            onClick = {
                viewModel.setEvent(HomeContract.Event.SaveTip)
            }
        )
    }
}