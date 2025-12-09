package com.kutayeter.dailylingua.utils

import android.content.Context
import com.kutayeter.dailylingua.data.model.Word
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.IOException

object JsonUtils {
    private val gson = Gson()

    data class WordsContainer(val words: List<Word>)

    fun loadWordsFromAssets(context: Context, fileName: String): List<Word> {
        val actualFile = when (fileName) {
            "en", "words_en.json" -> "words_en.json"
            "de", "words_de.json" -> "words_de.json"
            "ru", "words_ru.json" -> "words_ru.json"
            else -> fileName
        }

        val json = try {
            context.assets.open(actualFile).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            // fallback to English
            try {
                context.assets.open("words_en.json").bufferedReader().use { it.readText() }
            } catch (e2: IOException) {
                return emptyList()
            }
        }

        return try {
            val type = object : TypeToken<WordsContainer>() {}.type
            val container: WordsContainer = gson.fromJson(json, type)
            container.words
        } catch (e: JsonSyntaxException) {
            // fallback: try parsing as plain list
            try {
                val type = object : TypeToken<List<Word>>() {}.type
                gson.fromJson(json, type)
            } catch (ex: Exception) {
                emptyList()
            }
        }
    }
}
