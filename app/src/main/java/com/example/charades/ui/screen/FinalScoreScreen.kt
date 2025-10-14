package com.example.charades.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FinalScoreScreen(
    playerScores: Map<Int, Int>,
    playerTotalTimes: Map<Int, Int>,
    onPlayAgain: () -> Unit,
    finalTitle: String,
    btnPlayAgain: String,
    gradientBrush: Brush,
    buttonColor: Color,
    geometricPatternColor: Color
) {
    val sortedScores = playerScores.toList().sortedByDescending { it.second }

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
            text = finalTitle,
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(sortedScores) { index, (player, score) ->
                val totalTime = playerTotalTimes[player] ?: 0
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Jugador ${player}", color = Color.White, fontSize = 20.sp)
                    Text(text = "$score (${totalTime}s)", color = Color.White, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onPlayAgain,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text(text = btnPlayAgain, color = Color.White)
        }
    }
}