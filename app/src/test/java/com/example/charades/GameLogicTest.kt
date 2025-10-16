
package com.example.charades

import com.example.charades.model.Words
import org.junit.Test
import org.junit.Assert.*

class GameLogicTest {

    @Test
    fun `word list is shuffled`() {
        val language = "en"
        val categoryWords = Words.animals[language] ?: emptyList()

        val shuffledWords1 = categoryWords.shuffled()
        val shuffledWords2 = categoryWords.shuffled()

        assertNotEquals(shuffledWords1, shuffledWords2)
    }
}
