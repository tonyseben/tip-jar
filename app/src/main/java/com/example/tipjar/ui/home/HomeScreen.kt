package com.example.tipjar.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tipjar.ui.home.component.HomeTopBar
import com.example.tipjar.ui.home.component.NumberPicker
import com.example.tipjar.ui.home.component.TJButton
import com.example.tipjar.ui.home.component.TJCheckBox
import com.example.tipjar.ui.home.component.TJTextField

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = { HomeTopBar() },
    ) { padding ->
        Column(
            modifier = Modifier.padding(
                start = 24.dp,
                top = padding.calculateTopPadding(),
                end = 24.dp,
                bottom = padding.calculateBottomPadding()
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

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

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Tip",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = state.value.totalTip,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Per Person",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = state.value.perPersonTip,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                Alignment.BottomStart
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TJCheckBox(label = "Take photo of receipt")
                    TJButton(
                        onClick = {
                            viewModel.setEvent(HomeContract.Event.SaveTip)
                        }
                    )
                }
            }
        }
    }
}