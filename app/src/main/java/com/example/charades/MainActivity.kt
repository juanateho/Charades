
package com.example.charades

import android.content.res.Configuration
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import java.util.Locale

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
    var guessedWords by remember { mutableStateOf(listOf<Triple<String, Int, String>>()) } // Word, Time, Status("guessed" or "passed")
    var lastTimeLeft by remember { mutableStateOf(60) }

    val locale = Locale(language)
    val context = LocalContext.current
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)
    val localizedContext = context.createConfigurationContext(configuration)

    CompositionLocalProvider(LocalContext provides localizedContext) {
        val darkGradientBrush = Brush.verticalGradient(
            colors = listOf(Color(0xFF0D47A1), Color(0xFF4A148C))
        )

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
                val btnStart = stringResource(R.string.btn_start)
                val btnLanguage = if (language == "en") "Language: English" else "Idioma: Español"
                val btnToggleLanguage = if (language == "en") "Cambiar a Español" else "Switch to English"
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(darkGradientBrush) // Using dark gradient
                            .padding(32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White // White for dark theme
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(text = btnLanguage, fontSize = 18.sp, color = Color.White) // White for dark theme
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                language = if (language == "en") "es" else "en"
                            },
                            modifier = Modifier.fillMaxWidth().height(56.dp), // Increased size
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(text = btnToggleLanguage, color = Color.White, fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                screen = "category"
                            },
                            modifier = Modifier.fillMaxWidth().height(56.dp), // Increased size
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Text(text = btnStart, color = Color.White, fontSize = 18.sp)
                        }
                    }
                }
            }
            "category" -> {
                val btnBack = if (language == "en") "Back" else "Volver"
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(darkGradientBrush) // Using dark gradient
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
                    CategoryMenuScreen { category ->
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
                val timerText = stringResource(R.string.timer)
                val scoreText = stringResource(R.string.score)
                val btnCorrect = stringResource(R.string.btn_correct)
                val btnPass = if (language == "en") "Pass" else "Pasar" // New button text

                GameScreen(
                    category = selectedCategory,
                    language = language,
                    onCorrect = {
                        score += 1
                        guessedWords = guessedWords + Triple(currentWord, (lastTimeLeft - timeLeft), "guessed")
                        round += 1
                        lastTimeLeft = timeLeft
                        if (round < wordList.size) {
                            currentWord = wordList[round]
                        } else {
                            timerRunning = false
                            screen = "result"
                        }
                    },
                    onPass = { // New onPass lambda
                        guessedWords = guessedWords + Triple(currentWord, (lastTimeLeft - timeLeft), "passed")
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
                    btnCorrect = btnCorrect,
                    btnPass = btnPass, // Pass the new button text
                    gradientBrush = darkGradientBrush // Pass the gradient
                )
            }
            "result" -> {
                val resultTitle = stringResource(R.string.result_title)
                val finalScore = stringResource(R.string.final_score)
                val btnRestart = stringResource(R.string.btn_restart)
                val wordHeader = if (language == "en") "Word" else "Palabra"
                val timeHeader = if (language == "en") "Time (s)" else "Tiempo (s)"
                val statusHeader = if (language == "en") "Status" else "Estado" // For result table

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
                    guessedWords = guessedWords, // This is now List<Triple<String, Int, String>>
                    wordHeader = wordHeader,
                    timeHeader = timeHeader,
                    statusHeader = statusHeader,
                    gradientBrush = darkGradientBrush
                )
            }
        }
    }
}