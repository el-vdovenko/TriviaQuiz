package com.vdovenko.triviaquiz.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnswerButton(
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color = CardBlue,
    borderColor: Color = CardBorderBlue,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = LightBlue,
            disabledContainerColor = containerColor,
            disabledContentColor = LightBlue,
        ),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(2.dp, borderColor),
        onClick = { onClick() },
        enabled = enabled
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = text
        )
    }
}