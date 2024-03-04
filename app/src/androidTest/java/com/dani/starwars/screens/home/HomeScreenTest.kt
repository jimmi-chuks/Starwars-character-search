package com.dani.starwars.screens.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.dani.starwars.R
import org.junit.Rule

import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun verify_Top_section_visible(){
        composeTestRule.setContent {
            HomeScreenContent(
                onBackPressed = {},
                performSearch = { },
                clearRecentSearches = {},
                onSearchActive = {},
                onSearchQueryUpdated = {}
            )
        }

        composeTestRule.onNodeWithTag("Top Section")
            .assertExists("Top Section composable not present")

        val appLabel = composeTestRule.activity.getString(R.string.app_label)
        composeTestRule.onNodeWithText(appLabel)
            .assertExists("String with Text $appLabel not present")

        val intro = composeTestRule.activity.getString(R.string.character_search_intro)
        composeTestRule.onNodeWithText(intro)
            .assertExists("Intro with Text $intro not present")

        val searchTutorial = composeTestRule.activity.getString(R.string.character_search_tutorial)
        composeTestRule.onNodeWithText(searchTutorial)
            .assertExists("Search tutorial with Text $searchTutorial not present")
    }

    @Test
    fun verify_Top_section_not_visible(){
        composeTestRule.setContent {
            HomeScreenContent(
                fullScreenSearchActive = true,
                onBackPressed = {},
                performSearch = { },
                clearRecentSearches = {},
                onSearchActive = {},
                onSearchQueryUpdated = {}
            )
        }

        composeTestRule.onNodeWithTag("Top Section")
            .assertDoesNotExist()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.app_label))
            .assertDoesNotExist()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.character_search_intro))
            .assertDoesNotExist()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.character_search_tutorial))
            .assertDoesNotExist()
    }



}