package com.example.tipjar.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tipjar.ui.component.NumberPicker
import com.example.tipjar.ui.component.TJButton
import com.example.tipjar.ui.component.TJCheckBox
import com.example.tipjar.ui.component.TJTextField

@Composable
@Preview(showBackground = true)
fun HomeScreen() {
    Column {

        TJTextField(label = "Enter amount")
        NumberPicker(label = "How many people?")
        TJTextField(label = "% TIP")

        Row {
            Text(text = "Total Tip")
            Text(text = "$20.00")
        }

        Row {
            Text(text = "Per Person")
            Text(text = "$10.00")
        }

        TJCheckBox(label = "Take photo of receipt")
        TJButton()
    }
}