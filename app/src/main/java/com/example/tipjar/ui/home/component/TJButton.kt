package com.example.tipjar.ui.home.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TJButton(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = "Save Payment")
    }
}