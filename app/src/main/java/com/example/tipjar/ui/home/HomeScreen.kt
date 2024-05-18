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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tipjar.R
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
                    Toast.makeText(
                        context,
                        stringResource(R.string.statusTransactSaved),
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("history")
                } else {
                    Toast.makeText(
                        context,
                        stringResource(R.string.statusTransactSavedNoReceipt),
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
                    Toast.makeText(
                        context,
                        context.getString(R.string.statusTransactSaved),
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("history")
                }

                HomeContract.SideEffect.SaveTipFailed -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.statusTransactSaveFailed),
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
                label = stringResource(R.string.labelAmount),
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
                label = stringResource(R.string.labelPax),
                number = state.value.persons.toInt(),
                onClick = {
                    viewModel.setEvent(HomeContract.Event.OnPersonCountChange(it))
                }
            )

            TJTextField(
                label = stringResource(R.string.labelTipPercent),
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
                    Text(text = stringResource(R.string.labelTotalTip))
                    Text(text = "${state.value.currency} ${state.value.totalTip}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.labelPaxTip),
                        fontSize = 18.sp
                    )
                    Text(
                        text = "${state.value.currency} ${state.value.perPersonTip}",
                        fontSize = 18.sp
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
                        label = stringResource(R.string.labelTakePhoto),
                        isChecked = state.value.isReceipt,
                        onCheckedChange = {
                            viewModel.setEvent(HomeContract.Event.OnReceiptChange)
                        }
                    )
                    TJButton(
                        label = stringResource(R.string.actionSavePayment),
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