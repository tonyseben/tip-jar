package com.example.tipjar.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
fun NumberPicker(label: String = "", start: Int = 0) {
    var count = start

    Column {
        Text(text = label)
        Row {
            OpButton(operator = '+')
            Text(
                text = count.toString(),
                modifier = Modifier.weight(1F),
                textAlign = TextAlign.Center
            )
            OpButton(operator = '-')
        }
    }
}

@Composable
fun OpButton(operator: Char) {
    Button(
        modifier = Modifier.size(24.dp),
        onClick = { /*TODO*/ }) {
        Text(text = operator.toString())
    }
}