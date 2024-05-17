package com.example.tipjar.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tipjar.R

@Composable
fun HistoryDetailScreen(navController: NavController, timestamp: Long, onDismiss: () -> Unit) {
    val viewModel: HistoryDetailViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    viewModel.getHistoryDetail(timestamp)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .align(Alignment.Center)
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                ) {
                    state.value.receiptUri?.let { receiptUri ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(receiptUri)
                                .build(),
                            modifier = Modifier.height(350.dp),
                            contentDescription = "Receipt",
                            contentScale = ContentScale.Crop
                        )
                    } ?: Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.White)
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = "No Receipt image available",
                            fontSize = 13.sp,
                            modifier = Modifier.weight(1F),
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(onClick = {
                            navController.navigate("camera/${state.value.timestamp}")
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = "Camera"
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(90.dp)
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = state.value.dateTime,
                        modifier = Modifier.align(Alignment.TopStart),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = state.value.amount,
                        modifier = Modifier.align(Alignment.BottomStart),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tip: " + state.value.totalTip,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .alpha(0.5F),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }
}