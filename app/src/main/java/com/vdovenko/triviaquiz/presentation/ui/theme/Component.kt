package com.vdovenko.triviaquiz.presentation.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnswerButton(
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color = BlueCard,
    borderColor: Color = BlueCardBorder,
    textColor: Color = BlueLight,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = BlueLight,
            disabledContainerColor = containerColor,
            disabledContentColor = BlueLight,
        ),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(2.dp, borderColor),
        onClick = { onClick() },
        enabled = enabled
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            color = textColor,
            text = text
        )
    }
}

@Composable
fun CategoryAnswer(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonColors(
            containerColor = BlueCard,
            contentColor = BlueLight,
            disabledContainerColor = BlueCard,
            disabledContentColor = BlueLight,
        ),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(2.dp, BlueCardBorder),
        onClick = { onClick() },
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = BlueLight,
            text = text
        )
    }
}