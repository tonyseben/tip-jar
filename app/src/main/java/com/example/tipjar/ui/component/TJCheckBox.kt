package com.example.tipjar.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TJCheckBox(label: String = "", isChecked: Boolean = false, onCheckedChange: (Boolean) -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )

        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
