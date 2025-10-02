package com.example.charades.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(
    category: String,
    language: String,
    onCorrect: () -> Unit,
    onPass: () -> Unit,
    onTimeUp: () -> Unit,
    word: String,
    score: Int,
    timeLeft: Int,
    timerText: String,
    scoreText: String,
    btnCorrect: String,
    btnPass: String,
    gradientBrush: Brush
) {
    val passTextColor = Color(0xFFD50000) // Vibrant Red for Pass text
    val correctTextColor = Color(0xFF00C853) // Vibrant Green for Correct text

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onPass,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        drawLine(
                            color = passTextColor,
                            start = Offset(size.width - strokeWidth / 2, 0f),
                            end = Offset(size.width - strokeWidth / 2, size.height),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Square
                        )
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // Fully transparent background
                    contentColor = passTextColor // Text color provides the shine
                ),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(text = btnPass, fontSize = 24.sp)
            }

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = word,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "$timerText: $timeLeft",
                    fontSize = 20.sp,
                    color = if (timeLeft <= 5) Color.Red else Color.White
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "$scoreText: $score",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Button(
                onClick = onCorrect,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        drawLine(
                            color = correctTextColor,
                            start = Offset(strokeWidth / 2, 0f),
                            end = Offset(strokeWidth / 2, size.height),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Square
                        )
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // Fully transparent background
                    contentColor = correctTextColor // Text color provides the shine
                ),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(text = btnCorrect, fontSize = 24.sp)
            }
        }
    }
}
