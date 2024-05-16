package com.example.tipjar.ui.history.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipjar.R
import com.example.tipjar.ui.home.HistoryItemUiState

@Composable
fun HistoryItem(history: HistoryItemUiState) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = history.dateTime,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.TopStart),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = history.amount,
            modifier = Modifier.align(Alignment.BottomStart),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Tip: " + history.totalTip,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .alpha(0.5F),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(id = R.drawable.ic_history),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 8.dp)
                .size(52.dp)
                .align(Alignment.CenterEnd)
        )
    }
}