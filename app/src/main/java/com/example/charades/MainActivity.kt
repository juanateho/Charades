
package com.example.charades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.charades.ui.screen.CategoryMenuScreen
import com.example.charades.ui.screen.GameScreen
import com.example.charades.ui.screen.ResultScreen
import com.example.charades.model.Words
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.charades.ui.theme.CharadesTheme
import com.example.charades.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CharadesTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var language by remember { mutableStateOf("en") }
    var screen by remember { mutableStateOf("main") }
    var selectedCategory by remember { mutableStateOf("") }
    var score by remember { mutableStateOf(0) }
    var round by remember { mutableStateOf(0) }
    var wordList by remember { mutableStateOf(listOf<String>()) }
    var currentWord by remember { mutableStateOf("") }
    var timeLeft by remember { mutableStateOf(60) }
    var timerRunning by remember { mutableStateOf(false) }
    var guessedWords by remember { mutableStateOf(listOf<Pair<String, Int>>()) }
    var lastTimeLeft by remember { mutableStateOf(60) }

    // Timer effect
    LaunchedEffect(timerRunning, timeLeft, screen) {
        if (timerRunning && timeLeft > 0 && screen == "game") {
            kotlinx.coroutines.delay(1000)
            timeLeft -= 1
        } else if (timerRunning && timeLeft == 0 && screen == "game") {
            timerRunning = false
            screen = "result"
        }
    }

    when (screen) {
        "main" -> {
            val title = if (language == "en") "CHARADES" else "CHARADAS"
            val btnStart = if (language == "en") "Start game" else "Iniciar juego"
            val btnLanguage = if (language == "en") "Language: English" else "Idioma: Español"
            val btnToggleLanguage = if (language == "en") "Cambiar a Español" else "Switch to English"
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
                        text = title,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(text = btnLanguage, fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            language = if (language == "en") "es" else "en"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = btnToggleLanguage, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            screen = "category"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(text = btnStart, color = Color.White)
                    }
                }
            }
        }
        "category" -> {
            val btnBack = if (language == "en") "Back" else "Volver"
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(32.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Button(
                        onClick = { screen = "main" },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = btnBack, color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                CategoryMenuScreen(language = language) { category ->
                    selectedCategory = category
                    val key = when (category.lowercase()) {
                        "animales", "animals" -> "animals"
                        "películas", "movies" -> "movies"
                        "profesiones", "professions" -> "professions"
                        else -> ""
                    }
                    wordList = when (key) {
                        "animals" -> Words.animals[language] ?: listOf()
                        "movies" -> Words.movies[language] ?: listOf()
                        "professions" -> Words.professions[language] ?: listOf()
                        else -> listOf()
                    }.shuffled()
                    round = 0
                    score = 0
                    timeLeft = 60
                    currentWord = if (wordList.isNotEmpty()) wordList[0] else ""
                    guessedWords = listOf()
                    lastTimeLeft = 60
                    timerRunning = true
                    screen = "game"
                }
            }
        }
        "game" -> {
            val timerText = if (language == "en") "Time left" else "Tiempo restante"
            val scoreText = if (language == "en") "Score" else "Puntaje"
            val btnCorrect = if (language == "en") "Correct!" else "¡Acierto!"
            GameScreen(
                category = selectedCategory,
                language = language,
                onCorrect = {
                    score += 1
                    guessedWords = guessedWords + (currentWord to (lastTimeLeft - timeLeft))
                    round += 1
                    lastTimeLeft = timeLeft
                    if (round < wordList.size) {
                        currentWord = wordList[round]
                    } else {
                        timerRunning = false
                        screen = "result"
                    }
                },
                onTimeUp = {
                    timerRunning = false
                    screen = "result"
                },
                word = currentWord,
                score = score,
                timeLeft = timeLeft,
                timerText = timerText,
                scoreText = scoreText,
                btnCorrect = btnCorrect
            )
        }
        "result" -> {
            val resultTitle = if (language == "en") "Results" else "Resultados"
            val finalScore = if (language == "en") "Final score" else "Puntaje final"
            val btnRestart = if (language == "en") "Restart game" else "Reiniciar juego"
            val wordHeader = if (language == "en") "Word" else "Palabra"
            val timeHeader = if (language == "en") "Time (s)" else "Tiempo (s)"
            ResultScreen(
                score = score,
                onRestart = {
                    screen = "main"
                    score = 0
                    round = 0
                    selectedCategory = ""
                    wordList = listOf()
                    currentWord = ""
                    timeLeft = 60
                    timerRunning = false
                    guessedWords = listOf()
                    lastTimeLeft = 60
                },
                resultTitle = resultTitle,
                finalScore = finalScore,
                btnRestart = btnRestart,
                guessedWords = guessedWords,
                wordHeader = wordHeader,
                timeHeader = timeHeader
            )
        }
    }
}