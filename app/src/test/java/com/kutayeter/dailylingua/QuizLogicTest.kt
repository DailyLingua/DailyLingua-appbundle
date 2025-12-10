package com.kutayeter.dailylingua

import com.kutayeter.dailylingua.data.model.Word
import com.kutayeter.dailylingua.ui.quiz.QuizLogic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizLogicTest {

    private val sampleWord = Word(
        id = 1,
        word = "hello",
        translation = "merhaba",
        example = "hello world",
        exampleTranslation = "merhaba dünya",
        options = listOf("hello", "hola", "ciao", "bonjour"),
        correct = "hello"
    )

    @Test
    fun correctSelectionReturnsTrue() {
        assertTrue(QuizLogic.isCorrectSelection("hello", sampleWord.correct))
    }

    @Test
    fun wrongSelectionReturnsFalse() {
        assertFalse(QuizLogic.isCorrectSelection("hola", sampleWord.correct))
    }

    @Test
    fun scoreIncrementsOnCorrectAnswer() {
        val updated = QuizLogic.updatedScore(currentScore = 2, isCorrect = true)
        assertEquals(3, updated)
    }

    @Test
    fun scoreNotChangedOnWrongAnswer() {
        val updated = QuizLogic.updatedScore(currentScore = 2, isCorrect = false)
        assertEquals(2, updated)
    }

    @Test
    fun nextIndexAdvances() {
        assertEquals(1, QuizLogic.nextIndex(0))
    }

    @Test
    fun currentWordReturnsItemAtIndex() {
        val list = listOf(sampleWord)
        val word = QuizLogic.currentWord(list, 0)
        assertEquals(sampleWord, word)
    }
}
