package com.example.tipjar.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tipjar.ui.history.component.HistoryItem
import com.example.tipjar.ui.history.component.HistoryTopBar

@Composable
fun HistoryScreen(navController: NavController) {
    val viewModel: HistoryViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = { HistoryTopBar(navController) },
    ) { padding ->
        Column(
            modifier = Modifier.padding(
                start = 24.dp,
                top = padding.calculateTopPadding(),
                end = 24.dp,
                bottom = padding.calculateBottomPadding()
            ),
        ) {
            LazyColumn {
                items(state.value.history.size) {
                    HistoryItem(history = state.value.history[it])
                }
            }
        }
    }
}