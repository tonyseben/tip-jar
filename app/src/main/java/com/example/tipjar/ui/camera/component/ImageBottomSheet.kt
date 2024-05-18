package com.example.tipjar.ui.camera.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tipjar.R
import com.example.tipjar.ui.component.TJButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageBottomSheet(
    bitmap: Bitmap,
    isProgress: Boolean,
    onSaveClick: () -> Unit,
    onDiscardClick: () -> Unit
) {

    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { onDiscardClick() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .align(Alignment.BottomStart)
            ) {
                TJButton(
                    label = stringResource(R.string.actionRetake),
                    isEnabled = !isProgress,
                    onClick = onDiscardClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
                TJButton(
                    label = stringResource(R.string.actionSaveReceipt),
                    isEnabled = !isProgress,
                    onClick = onSaveClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }

            if (isProgress) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}