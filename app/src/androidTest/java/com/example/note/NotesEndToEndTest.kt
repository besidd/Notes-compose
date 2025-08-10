package com.example.note

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.note.ui.theme.core.Constants
import com.example.note.ui.theme.di.AppModule
import com.example.note.ui.theme.feature_note.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltTestRule.inject()
    }

        @Test
    fun saveNewNote_editAfterWards() {

        // click on FAB
        composeRule.onNodeWithContentDescription("Add a note").performClick()
        composeRule
            .onNodeWithTag(Constants.TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(Constants.CONTENT_TEXT_FIELD)
            .performTextInput("test-content")

        composeRule.onNodeWithContentDescription("Save").performClick()

        // check if new note is displayed
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        // click on new note
        composeRule.onNodeWithText("test-title").performClick()

        // perform edit
        composeRule.onNodeWithTag(Constants.TITLE_TEXT_FIELD).assertTextEquals("test-title")
        composeRule.onNodeWithTag(Constants.CONTENT_TEXT_FIELD).assertTextEquals("test-content")
        composeRule.onNodeWithTag(Constants.TITLE_TEXT_FIELD).performTextInput("2")

        // update the note
        composeRule.onNodeWithContentDescription("Save").performClick()

        // check if changes are done
        composeRule.onNodeWithText("2test-title").assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByDescending() {
        for (i in 1..3) {
            composeRule.onNodeWithContentDescription("Add a note").performClick()
            composeRule
                .onNodeWithTag(Constants.TITLE_TEXT_FIELD)
                .performTextInput("$i")
            composeRule
                .onNodeWithTag(Constants.CONTENT_TEXT_FIELD)
                .performTextInput(" .")
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sorting").performClick()
        composeRule.onNodeWithContentDescription("Title").performClick()
        composeRule.onNodeWithContentDescription("Descending").performClick()

        composeRule.onAllNodesWithTag(Constants.NOTE_ITEM)[0]
            .assertTextContains("3")
        composeRule.onAllNodesWithTag(Constants.NOTE_ITEM)[1]
            .assertTextContains("2")
        composeRule.onAllNodesWithTag(Constants.NOTE_ITEM)[2]
            .assertTextContains("1")


    }
}