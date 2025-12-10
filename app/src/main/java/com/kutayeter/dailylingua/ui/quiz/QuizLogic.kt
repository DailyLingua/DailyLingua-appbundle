package com.kutayeter.dailylingua.ui.quiz

import com.kutayeter.dailylingua.data.model.Word

/**
 * Small helper for quiz state so we can unit-test answer evaluation and scoring without Android UI.
 */
object QuizLogic {
    fun isCorrectSelection(selected: String, correct: String): Boolean = selected == correct

    fun updatedScore(currentScore: Int, isCorrect: Boolean): Int = if (isCorrect) currentScore + 1 else currentScore

    fun nextIndex(currentIndex: Int): Int = currentIndex + 1

    fun currentWord(words: List<Word>, index: Int): Word? = words.getOrNull(index)
}
