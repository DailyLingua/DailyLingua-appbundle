package com.kutayeter.dailylingua

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kutayeter.dailylingua.ui.dictionary.DictionaryActivity
import com.kutayeter.dailylingua.ui.language.LanguageSelectActivity
import com.kutayeter.dailylingua.ui.progress.ProgressActivity
import com.kutayeter.dailylingua.ui.quiz.QuizActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import android.view.View

@RunWith(AndroidJUnit4::class)
class E2EViewsTest {

    private fun prefs() = ApplicationProvider.getApplicationContext<Context>()
        .getSharedPreferences("daily_prefs", Context.MODE_PRIVATE)

    @Before
    fun seedLanguage() {
        prefs().edit().clear().putString("selectedLanguage", "en").apply()
    }

    private fun waitFor(delayMs: Long): ViewAction = object : ViewAction {
        override fun getDescription() = "wait for ${delayMs}ms"
        override fun getConstraints() = isRoot()
        override fun perform(uiController: UiController, view: View?) {
            uiController.loopMainThreadForAtLeast(delayMs)
        }
    }

    @Test
    fun languageSelectButtonsVisible() {
        ActivityScenario.launch(LanguageSelectActivity::class.java).use {
            onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(5)))
            onView(withId(R.id.btn_en)).check(matches(isDisplayed()))
            onView(withId(R.id.btn_de)).check(matches(isDisplayed()))
            onView(withId(R.id.btn_ru)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun quizActivity_showsQuestionAndAllowsNext() {
        ActivityScenario.launch(QuizActivity::class.java).use {
            onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(5)))
            onView(withId(R.id.tv_question)).check(matches(isDisplayed()))
            onView(withId(R.id.btn_a)).perform(click())
            onView(withId(R.id.btn_next)).perform(click())
            onView(withId(R.id.tv_question)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun dictionaryActivity_listsWords() {
        ActivityScenario.launch(DictionaryActivity::class.java).use {
            onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(5)))
            onView(withId(R.id.rv_dictionary)).check(matches(isDisplayed()))
            onView(withId(R.id.rv_dictionary)).check(matches(hasDescendant(withId(R.id.tv_item_word))))
        }
    }

    @Test
    fun progressActivity_showsPercentAndCounts() {
        prefs().edit()
            .putString("selectedLanguage", "en")
            .putInt("total_questions_en", 5)
            .putInt("total_correct_en", 3)
            .apply()

        ActivityScenario.launch(ProgressActivity::class.java).use {
            onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(5)))
            onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
            onView(withId(R.id.tv_percent)).check(matches(isDisplayed()))
            onView(withId(R.id.tv_correct_count)).check(matches(isDisplayed()))
            onView(withId(R.id.tv_wrong_count)).check(matches(isDisplayed()))
        }
    }
}
