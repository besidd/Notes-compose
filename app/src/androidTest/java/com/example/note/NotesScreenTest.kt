package com.example.note

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.note.ui.theme.NoteTheme
import com.example.note.ui.theme.core.Constants
import com.example.note.ui.theme.di.AppModule
import com.example.note.ui.theme.feature_note.presentation.MainActivity
import com.example.note.ui.theme.feature_note.presentation.notes.components.NotesScreen
import com.example.note.ui.theme.feature_note.presentation.uti.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltTestRule.inject()

    }

    @Test
    fun clickToggleOrderSelection_isVisible() {
        composeRule.onNodeWithTag(Constants.ORDER_SCREEN_TEST_TAG).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sorting").performClick()
        composeRule.onNodeWithTag(Constants.ORDER_SCREEN_TEST_TAG).assertIsDisplayed()

    }

}