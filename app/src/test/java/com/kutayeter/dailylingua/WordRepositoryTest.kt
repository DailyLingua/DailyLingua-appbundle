package com.kutayeter.dailylingua

import android.content.Context
import com.kutayeter.dailylingua.data.model.Word
import com.kutayeter.dailylingua.data.repository.WordRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock

class WordRepositoryTest {

    private fun fakeWords(count: Int): List<Word> = (1..count).map {
        Word(
            id = it,
            word = "w$it",
            translation = "t$it",
            example = "ex $it",
            exampleTranslation = "ex tr $it",
            options = listOf("a", "b", "c", "d"),
            correct = "a"
        )
    }

    @Test
    fun randomQuizWordsReturnsRequestedCount() {
        val context = mock(Context::class.java)
        val repo = WordRepository(context, "en", wordsOverride = fakeWords(20))
        val list = repo.randomQuizWords(10)
        assertEquals(10, list.size)
        assertTrue(list.all { it.options.size >= 4 })
    }
}
