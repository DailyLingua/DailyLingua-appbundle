package com.kutayeter.dailylingua

import com.kutayeter.dailylingua.utils.JsonUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.ByteArrayInputStream

class JsonUtilsTest {
    @Test
    fun parsesWordsEnvelope() {
                val json = """
                        {
                            "words": [
                                {
                                    "word": "hello",
                                    "translation": "merhaba",
                                    "example": "hello world",
                                    "exampleTranslation": "merhaba dünya",
                                    "options": ["hello", "hola", "hallo", "ciao"],
                                    "correct": "hello"
                                }
                            ]
                        }
                """.trimIndent()
        val stream = ByteArrayInputStream(json.toByteArray())
                val words = JsonUtils.parseWords(stream)
        assertEquals(1, words.size)
        assertEquals("hello", words[0].word)
        assertEquals("merhaba", words[0].translation)
        assertTrue(words[0].options.contains("hello"))
    }
}
