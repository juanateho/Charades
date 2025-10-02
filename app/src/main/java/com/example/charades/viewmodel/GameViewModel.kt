package com.example.charades.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charades.data.Words
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val _timer = MutableStateFlow(60)
    val timer: StateFlow<Int> = _timer

    private val _word = MutableStateFlow("")
    val word: StateFlow<String> = _word

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _gameFinished = MutableStateFlow(false)
    val gameFinished: StateFlow<Boolean> = _gameFinished

    private var timerJob: Job? = null
    private var wordsForCategory: List<String> = emptyList()
    private val usedWords = mutableSetOf<String>()

    fun startGame(category: String) {
        resetGame()
        wordsForCategory = Words.categories[category]?.shuffled() ?: emptyList()
        showNextWord()
        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_timer.value > 0) {
                delay(1000)
                _timer.value--
            }
            endGame()
        }
    }

    fun onCorrect() {
        _score.value++
        showNextWord()
    }

    fun onSkip() {
        showNextWord()
    }

    private fun showNextWord() {
        val nextWord = wordsForCategory.firstOrNull { it !in usedWords }

        if (nextWord != null) {
            _word.value = nextWord
            usedWords.add(nextWord)
        } else {
            endGame()
        }
    }

    private fun endGame() {
        timerJob?.cancel()
        _gameFinished.value = true
    }

    private fun resetGame() {
        timerJob?.cancel()
        _timer.value = 60
        _score.value = 0
        _word.value = ""
        usedWords.clear()
        _gameFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}