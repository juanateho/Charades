package com.example.charades.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.charades.R

@Composable
fun ResultScreen(
    score: Int,
    onRestart: () -> Unit,
    resultTitle: String,
    finalScore: String,
    btnRestart: String,
    guessedWords: List<Triple<String, Int, String>>,
    wordHeader: String,
    timeHeader: String,
    statusHeader: String,
    gradientBrush: Brush,
    buttonColor: Color // Added buttonColor parameter
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = resultTitle,
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "$finalScore: $score",
                fontSize = 24.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (guessedWords.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header column
                    Column {
                        Text(wordHeader, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(timeHeader, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(statusHeader, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    // Data columns
                    guessedWords.forEach { (word, time, status) ->
                        val statusText = if (status == "guessed") {
                            stringResource(R.string.status_guessed)
                        } else {
                            stringResource(R.string.status_passed)
                        }
                        Column {
                            Text(word, color = Color.White)
                            Text(time.toString(), color = Color.White)
                            Text(
                                text = statusText,
                                color = if (status == "guessed") Color.Green else Color.Red
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onRestart,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor) // Use passed buttonColor
            ) {
                Text(text = btnRestart, color = Color.White)
            }
        }
    }
}
