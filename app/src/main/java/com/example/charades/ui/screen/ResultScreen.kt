package com.example.charades.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
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
    onContinue: () -> Unit,
    resultTitle: String,
    finalScore: String,
    btnContinue: String,
    guessedWords: List<Triple<String, Int, String>>,
    wordHeader: String,
    timeHeader: String,
    statusHeader: String,
    gradientBrush: Brush,
    buttonColor: Color,
    geometricPatternColor: Color,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
                .drawBehind {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    drawCircle(
                        color = geometricPatternColor,
                        radius = canvasWidth * 0.6f,
                        center = Offset(x = canvasWidth * 0.2f, y = canvasHeight * 0.3f)
                    )
                    drawCircle(
                        color = geometricPatternColor,
                        radius = canvasWidth * 0.5f,
                        center = Offset(x = canvasWidth * 0.85f, y = canvasHeight * 0.7f)
                    )
                    drawCircle(
                        color = geometricPatternColor,
                        radius = canvasWidth * 0.4f,
                        center = Offset(x = canvasWidth * 0.5f, y = canvasHeight * 0.9f)
                    )
                }
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
                    modifier = Modifier.fillMaxWidth(0.6f).horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column {
                        Text(wordHeader, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(timeHeader, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(statusHeader, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    guessedWords.forEach { (word, time, status) ->
                        val (statusText, statusColor) = when (status) {
                            "guessed" -> stringResource(R.string.status_guessed) to Color.Green
                            "passed" -> stringResource(R.string.status_passed) to Color.Red
                            else -> stringResource(R.string.status_not_guessed) to Color.Yellow
                        }
                        Column {
                            Text(word, color = Color.White)
                            Text(time.toString(), color = Color.White)
                            Text(
                                text = statusText,
                                color = statusColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth(0.6f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text(text = btnContinue, color = Color.White)
            }
        }
    }
}