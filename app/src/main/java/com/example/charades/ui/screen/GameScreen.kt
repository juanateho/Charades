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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val passTextColor = Color(0xFFD50000)
private val correctTextColor = Color(0xFF00C853)
private val passButtonContainerColor = passTextColor.copy(alpha = 0.1f)
private val correctButtonContainerColor = correctTextColor.copy(alpha = 0.1f)

@Composable
fun GameScreen(
    playerName: String,
    onCorrect: () -> Unit,
    onPass: () -> Unit,
    word: String,
    score: Int,
    timeLeft: Int,
    timerText: String,
    scoreText: String,
    btnCorrect: String,
    btnPass: String,
    gradientBrush: Brush,
    geometricPatternColor: Color
) {
    Box(
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
    ) {
        Text(
            text = playerName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
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

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onPass,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.25f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = passButtonContainerColor,
                    contentColor = passTextColor
                ),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(text = btnPass, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.weight(0.5f))
            Button(
                onClick = onCorrect,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.25f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = correctButtonContainerColor,
                    contentColor = correctTextColor
                ),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(text = btnCorrect, fontSize = 24.sp)
            }
        }
    }
}