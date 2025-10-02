package com.example.charades.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.charades.R
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
	category: String,
	language: String,
	onCorrect: () -> Unit,
	onTimeUp: () -> Unit,
	word: String,
	score: Int,
	timeLeft: Int,
	timerText: String,
	scoreText: String,
	btnCorrect: String
) {
	// Temporizador visual y palabra
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background)
			.padding(32.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = word,
			fontSize = 36.sp,
			fontWeight = FontWeight.Bold,
			color = MaterialTheme.colorScheme.primary
		)
		Spacer(modifier = Modifier.height(24.dp))
		Text(
			text = "$timerText: $timeLeft",
			fontSize = 20.sp,
			color = if (timeLeft <= 5) Color.Red else MaterialTheme.colorScheme.onBackground
		)
		Spacer(modifier = Modifier.height(24.dp))
		Text(
			text = "$scoreText: $score",
			fontSize = 20.sp,
			color = MaterialTheme.colorScheme.secondary
		)
		Spacer(modifier = Modifier.height(32.dp))
		Button(
			onClick = onCorrect,
			colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
		) {
			Text(text = btnCorrect, color = Color.White)
		}
	}
}
