package com.example.tipjar.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.tipjar.ui.theme.offWhite
import com.example.tipjar.ui.theme.orange
import com.example.tipjar.ui.theme.orangeGrey
import com.example.tipjar.ui.theme.orangeLight
import com.example.tipjar.ui.theme.white

@Composable
fun TJButton(
    modifier: Modifier = Modifier,
    label: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isEnabled) {
                        listOf(orangeLight, orange)
                    } else {
                        listOf(orange, orangeGrey)
                    }
                ),
                shape = RoundedCornerShape(25)
            )
            .size(width = 48.dp, height = 48.dp),
        onClick = onClick,
        enabled = isEnabled
    ) {
        Text(
            text = label,
            color = if (isEnabled) white else offWhite
        )
    }
}