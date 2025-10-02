package com.example.charades.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.charades.R

@Composable
fun ResultScreen(
	score: Int,
	onRestart: () -> Unit,
	resultTitle: String,
	finalScore: String,
	btnRestart: String,
	guessedWords: List<Pair<String, Int>>,
	wordHeader: String,
	timeHeader: String
) {
	Surface(
		modifier = Modifier.fillMaxSize(),
		color = MaterialTheme.colorScheme.background
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(MaterialTheme.colorScheme.background)
				.padding(32.dp),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				text = resultTitle,
				fontSize = 32.sp,
				color = MaterialTheme.colorScheme.primary
			)
			Spacer(modifier = Modifier.height(24.dp))
			Text(
				text = "$finalScore: $score",
				fontSize = 24.sp,
				color = MaterialTheme.colorScheme.secondary
			)
			Spacer(modifier = Modifier.height(24.dp))
			if (guessedWords.isNotEmpty()) {
				Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
					Text(wordHeader, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
					Text(timeHeader, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
				}
				guessedWords.forEach { (word, time) ->
					Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
						Text(word, modifier = Modifier.weight(1f))
						Text(time.toString(), modifier = Modifier.weight(1f))
					}
				}
			}
			Spacer(modifier = Modifier.height(32.dp))
			Button(
				onClick = onRestart,
				colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
			) {
				Text(text = btnRestart, color = Color.White)
			}
		}
	}
}
