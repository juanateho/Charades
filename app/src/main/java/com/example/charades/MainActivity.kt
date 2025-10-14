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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
    var guessedWords by remember { mutableStateOf(listOf<Triple<String, Int, String>>()) }
    var lastTimeLeft by remember { mutableStateOf(60) }

    val locale = Locale(language)
    val context = LocalContext.current
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)
    val localizedContext = context.createConfigurationContext(configuration)

    CompositionLocalProvider(LocalContext provides localizedContext) {
        val newBackgroundBrush = SolidColor(Color(0xFF0E0929))
        val transparentButtonColor = Color(0xFF7F52FF).copy(alpha = 0.6f)
        val geometricPatternColor = Color(0xFF4A2A7E).copy(alpha = 0.15f)

        val drawGeometricPattern: Modifier.(Boolean) -> Modifier = { apply ->
            if (apply) {
                this.drawBehind { // 'this' refers to DrawScope
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
            } else this
        }

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

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(newBackgroundBrush)
                            .drawGeometricPattern(true)
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 32.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = title,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(
                                onClick = { screen = "category" },
                                modifier = Modifier.fillMaxWidth().height(56.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = transparentButtonColor)
                            ) {
                                Text(text = btnStart, color = Color.White, fontSize = 18.sp)
                            }
                        }
                        Button(
                            onClick = { language = if (language == "en") "es" else "en" },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = transparentButtonColor),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(text = "EN/ES", color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
            }
            "category" -> {
                val btnBack = if (language == "en") "Back" else "Volver"
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(newBackgroundBrush)
                        .drawGeometricPattern(true)
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
                            colors = ButtonDefaults.buttonColors(containerColor = transparentButtonColor)
                        ) {
                            Text(text = btnBack, color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    CategoryMenuScreen(
                        buttonColor = transparentButtonColor,
                        onCategorySelected = { category ->
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
                    )
                }
            }
            "game" -> {
                val timerText = stringResource(R.string.timer)
                val scoreText = stringResource(R.string.score)
                val btnCorrect = stringResource(R.string.btn_correct)
                val btnPass = if (language == "en") "Pass" else "Pasar"

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
                    onPass = {
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
                    btnPass = btnPass,
                    gradientBrush = newBackgroundBrush,
                    geometricPatternColor = geometricPatternColor
                )
            }
            "result" -> {
                val resultTitle = stringResource(R.string.result_title)
                val finalScore = stringResource(R.string.final_score)
                val btnRestart = stringResource(R.string.btn_restart)
                val wordHeader = if (language == "en") "Word" else "Palabra"
                val timeHeader = if (language == "en") "Time (s)" else "Tiempo (s)"
                val statusHeader = if (language == "en") "Status" else "Estado"

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
                    timeHeader = timeHeader,
                    statusHeader = statusHeader,
                    gradientBrush = newBackgroundBrush,
                    buttonColor = transparentButtonColor,
                    geometricPatternColor = geometricPatternColor
                )
            }
        }
    }
}
