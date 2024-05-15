package com.example.tipjar.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TJButton(
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isEnabled) {
                        listOf(
                            Color(0xFFF27A0A),
                            Color(0xFFD26E11),
                        )
                    } else {
                        listOf(
                            Color(0xFFF27A0A).copy(alpha = 0.5f),
                            Color(0xFFD26E11).copy(alpha = 0.5f),
                        )
                    }
                ),
                shape = RoundedCornerShape(25)
            )
            .size(width = 48.dp, height = 48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = onClick
    ) {
        Text(text = "Save Payment")
    }
}