package com.example.tipjar.ui.home

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tipjar.ui.component.NumberPicker
import com.example.tipjar.ui.component.TJButton
import com.example.tipjar.ui.component.TJCheckBox
import com.example.tipjar.ui.component.TJTextField
import com.example.tipjar.ui.home.component.HomeTopBar

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    navController.currentBackStackEntry?.savedStateHandle?.let {
        if (it.contains("isReceiptSaved")) {
            val isReceiptSaved = it.get<Boolean>("isReceiptSaved")
            it.remove<Boolean>("isReceiptSaved")

            isReceiptSaved?.let { saved ->
                if (saved) {
                    Toast.makeText(context, "Transaction saved!", Toast.LENGTH_SHORT).show()
                    navController.navigate("history")
                } else {
                    Toast.makeText(
                        context,
                        "Transaction saved! But, receipt not captured.",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("history")
                }
            }
        }
    }


    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                HomeContract.SideEffect.SaveTipSuccess -> {
                    Toast.makeText(context, "Transaction saved!", Toast.LENGTH_SHORT).show()
                    navController.navigate("history")
                }

                HomeContract.SideEffect.SaveTipFailed -> {
                    Toast.makeText(
                        context,
                        "Something went wrong. Could not save transaction.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is HomeContract.SideEffect.CaptureReceipt -> {
                    navController.currentBackStackEntry?.arguments?.apply {
                        putLong("timestamp", effect.timestamp)
                    }
                    navController.navigate("camera/" + effect.timestamp)
                }
            }
        }
    }

    Scaffold(
        topBar = { HomeTopBar(navController) },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(
                    start = 24.dp,
                    top = padding.calculateTopPadding(),
                    end = 24.dp,
                    bottom = padding.calculateBottomPadding() + 16.dp
                )
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            TJTextField(
                label = "Enter amount",
                value = state.value.amount,
                hint = "100.00",
                onChange = {
                    viewModel.setEvent(HomeContract.Event.OnAmountChange(it))
                },
                leadChar = state.value.currency,
                onLeadClick = {
                    viewModel.setEvent(HomeContract.Event.ChangeCurrency)
                },
            )

            NumberPicker(
                label = "How many people?",
                number = state.value.persons.toInt(),
                onClick = {
                    viewModel.setEvent(HomeContract.Event.OnPersonCountChange(it))
                }
            )

            TJTextField(
                label = "% TIP",
                value = state.value.tipPercentage,
                hint = "10",
                onChange = {
                    viewModel.setEvent(HomeContract.Event.OnTipPercentChange(it))
                },
                trailChar = "%"
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
                        text = "${state.value.currency} ${state.value.totalTip}",
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
                        text = "${state.value.currency} ${state.value.perPersonTip}",
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
                    TJCheckBox(
                        label = "Take photo of receipt",
                        isChecked = state.value.isReceipt,
                        onCheckedChange = {
                            viewModel.setEvent(HomeContract.Event.OnReceiptChange)
                        }
                    )
                    TJButton(
                        label = "Save Payment",
                        isEnabled = state.value.isSaveEnabled,
                        onClick = {
                            viewModel.setEvent(HomeContract.Event.SaveTip)
                        }
                    )
                }
            }
        }
    }
}