package com.example.tipjar.ui.home.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview(showBackground = true)
fun TJTextField(
    label: String = "Sample",
    value: String = "",
    onChange: ((String) -> Unit)? = null
) {

    //val regex = Regex("\\d*(\\.\\d{1,2})")
    TextField(
        value = value,
        onValueChange = {
            if (onChange != null) {
                //if(it.matches(regex)){
                onChange(it)
                //}
            }
        },
        label = {
            Text(
                text = label,
                modifier = Modifier.padding(bottom = 4.dp),
                fontSize = 12.sp
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        textStyle = TextStyle(
            fontSize = 12.sp
        ),
        singleLine = true
    )
}