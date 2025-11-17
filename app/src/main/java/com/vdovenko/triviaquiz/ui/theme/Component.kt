package com.vdovenko.triviaquiz.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
    conteinerColor: Color = CardBlue,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = conteinerColor,
            contentColor = LightBlue
        ),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(2.dp, CardBorderBlue),
        onClick = { onClick() },
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = text
        )
    }
}