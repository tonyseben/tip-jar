package com.example.tipjar.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
    label: String = "",
    value: String = "",
    hint: String = "",
    onChange: ((String) -> Unit) = {},
    leadChar: String? = null,
    onLeadClick: () -> Unit = {},
    trailChar: String? = null,
    onTrailClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = 4.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = value,
            placeholder = {
                if (!isFocused) {
                    Text(
                        text = hint,
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.5f),
                        textAlign = TextAlign.Center,
                        fontSize = 36.sp
                    )
                }
            },
            leadingIcon = {
                if (leadChar != null) {
                    IconButton(onClick = onLeadClick) {
                        Text(
                            text = leadChar,
                            fontSize = 24.sp
                        )
                    }
                }
            },
            trailingIcon = {
                if (trailChar != null) {
                    IconButton(onClick = onTrailClick) {
                        Text(
                            text = trailChar,
                            fontSize = 24.sp
                        )
                    }
                }
            },
            onValueChange = { onChange(it) },
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
            singleLine = true,
            interactionSource = interactionSource,
        )
    }

}