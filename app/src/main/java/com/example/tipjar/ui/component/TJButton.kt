package com.example.tipjar.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview(showBackground = true)
fun TJButton() {
    Button(onClick = { /*TODO*/ }) {
        Text(text = "Save Payment")
    }
}