package com.example.tipjar.ui.history.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tipjar.R
import com.example.tipjar.ui.home.HistoryItemUiState

@Composable
fun HistoryItem(
    history: HistoryItemUiState,
    onClick: (Long) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .clickable(onClick = { onClick(history.timestamp) })
    ) {
        Text(
            text = history.dateTime,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.TopStart)
        )
        Text(
            text = "${history.currency}${history.amount}",
            modifier = Modifier.align(Alignment.BottomStart),
            fontSize = 20.sp
        )
        Text(
            text = stringResource(R.string.tipValue, history.currency, history.totalTip),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .alpha(0.5F)
        )

        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(52.dp)
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(8.dp))
        ) {
            history.receiptUri?.let { receiptUri ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(receiptUri)
                        .build(),
                    contentDescription = stringResource(R.string.cdReceiptImage),
                    contentScale = ContentScale.Crop
                )
            }
        }

    }
}