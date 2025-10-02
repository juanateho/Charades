package com.example.charades.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.charades.navigation.Screen
import com.example.charades.viewmodel.GameViewModel

@Composable
fun GameScreen(navController: NavController, category: String) {
    val viewModel: GameViewModel = viewModel()
    val timer by viewModel.timer.collectAsState()
    val word by viewModel.word.collectAsState()
    val score by viewModel.score.collectAsState()
    val gameFinished by viewModel.gameFinished.collectAsState()

    LaunchedEffect(key1 = category) {
        viewModel.startGame(category)
    }

    if (gameFinished) {
        navController.navigate(Screen.ResultScreen.createRoute(score)) {
            popUpTo(Screen.CategoryScreen.route) { inclusive = false }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Tiempo: $timer")
        Text(text = word)
        Button(onClick = { viewModel.onCorrect() }) {
            Text(text = "Acierto")
        }
        Button(onClick = { viewModel.onSkip() }) {
            Text(text = "Error")
        }
    }
}