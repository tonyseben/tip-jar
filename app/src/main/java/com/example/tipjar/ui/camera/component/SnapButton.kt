package com.example.tipjar.ui.camera.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tipjar.R

@Composable
fun SnapButton(modifier: Modifier, onClick: () -> Unit = {}) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val borderColor = if (isPressed) Color(0xFFF27A0A) else Color(0xFFD2D2D2)

    OutlinedButton(
        modifier = modifier.size(72.dp),
        onClick = onClick,
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(50),
        interactionSource = interactionSource
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = "Take photo of receipt"
        )
    }
}