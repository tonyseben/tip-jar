package com.example.tipjar.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview(showBackground = true)
fun TJTextField(
    label: String = "Label",
    value: String = "",
    hint: String = "Hint",
    onChange: ((String) -> Unit)? = null
) {

    Column {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = 4.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = value,
            /*placeholder = {
                Text(
                    text = hint,
                    fontSize = 36.sp
                )
            },*/
            onValueChange = {
                if (onChange != null) {
                    onChange(it)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = 36.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF27A0A),
                unfocusedBorderColor = Color(0xFFD2D2D2)
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
    }

}