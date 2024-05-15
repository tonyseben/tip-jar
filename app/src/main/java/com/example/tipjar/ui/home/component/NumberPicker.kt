package com.example.tipjar.ui.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview(showBackground = true)
fun NumberPicker(label: String = "", start: String = "0", onClick: (Char) -> Unit = {}) {
    Column {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OpButton(operator = '+', onClick = onClick)
            Text(
                text = start,
                modifier = Modifier.weight(1F),
                fontSize = 36.sp,
                textAlign = TextAlign.Center
            )
            OpButton(operator = '-', onClick = onClick)
        }
    }
}

@Composable
fun OpButton(operator: Char, onClick: (Char) -> Unit = {}) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val borderColor = if (isPressed) Color(0xFFF27A0A) else Color(0xFFD2D2D2)

    OutlinedButton(
        modifier = Modifier.size(72.dp),
        onClick = { onClick(operator) },
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(50),
        interactionSource = interactionSource
    ) {
        Text(
            text = operator.toString(),
            fontSize = 36.sp,
            color = Color(0xFFF27A0A)
        )
    }
}