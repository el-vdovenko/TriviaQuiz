package com.vdovenko.triviaquiz.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.vdovenko.triviaquiz.presentation.ui.theme.BlueExtraDark
import com.vdovenko.triviaquiz.presentation.ui.theme.TriviaQuizTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            TriviaQuizTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BlueExtraDark)
                ) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}