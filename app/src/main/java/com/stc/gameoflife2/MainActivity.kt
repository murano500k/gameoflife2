package com.stc.gameoflife2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.stc.gameoflife2.theme.GameOfLife2Theme
import com.stc.gameoflife2.ui.screen.GameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameOfLife2Theme {
                GameScreen(modifier = Modifier.fillMaxSize())
            }
    }
    }
}