package com.mindera.rocketscience.spacex_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HeaderText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign? = null,
    textStyle: TextStyle = TextStyle(color = Color.White)
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black),
        text = text,
        style = textStyle,
        textAlign = textAlign
    )
}