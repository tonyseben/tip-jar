package com.example.tipjar.ui.history

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tipjar.R
import com.example.tipjar.ui.history.component.HistoryItem
import com.example.tipjar.ui.history.component.HistoryTopBar
import com.example.tipjar.ui.history.component.SwipeBox

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(navController: NavController) {
    val viewModel: HistoryViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    var itemSelected by remember { mutableLongStateOf(0L) }

    Scaffold(
        topBar = { HistoryTopBar(navController) },
    ) { padding ->
        Column(
            modifier = Modifier.padding(
                start = 24.dp,
                top = padding.calculateTopPadding(),
                end = 24.dp,
                bottom = padding.calculateBottomPadding()
            )
        ) {
            if (state.value.history.isNotEmpty()) {
                LazyColumn {
                    itemsIndexed(
                        items = state.value.history,
                        key = { _, item -> item.timestamp }
                    ) { index, item ->
                        var isRemoved by remember { mutableStateOf(false) }
                        if (!isRemoved) {
                            SwipeBox(
                                onDelete = {
                                    if (!isRemoved) {
                                        Log.d("Swipe", "Delete")
                                        viewModel.setEvent(HistoryContract.Event.Delete(index))
                                        isRemoved = true
                                    }
                                },
                                modifier = Modifier
                                    .animateItemPlacement()
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                HistoryItem(
                                    history = item,
                                    onClick = { timestamp ->
                                        itemSelected = timestamp
                                    })
                            }
                        }
                    }
                }

                if (itemSelected != 0L) {
                    HistoryDetailScreen(
                        navController,
                        itemSelected,
                        onDismiss = { itemSelected = 0L })
                }
            } else {
                Text(
                    text = stringResource(R.string.messageNoHistory),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

