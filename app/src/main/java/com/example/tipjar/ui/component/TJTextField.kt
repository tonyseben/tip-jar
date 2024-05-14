package com.example.tipjar.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview(showBackground = true)
fun TJTextField(label: String = "Sample", hint: String = "") {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = {
            Text(
                text = label,
                modifier = Modifier.padding(bottom = 4.dp),
                fontSize = 12.sp
            )
        },
        textStyle = TextStyle(
            fontSize = 12.sp // or whatever small size you're using
        ),
        singleLine = true
    )
}