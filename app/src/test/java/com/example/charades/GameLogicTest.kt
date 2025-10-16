
package com.example.charades

import com.example.charades.model.Words
import org.junit.Test
import org.junit.Assert.*

class GameLogicTest {

    @Test
    fun `word list is shuffled`() {
        val language = "en"
        val categoryWords = Words.animals[language] ?: emptyList()

        // When the words are shuffled twice
        val shuffledWords1 = categoryWords.shuffled()
        val shuffledWords2 = categoryWords.shuffled()

        // Then the two lists should not be identical
        assertNotEquals(shuffledWords1, shuffledWords2)
    }
}
