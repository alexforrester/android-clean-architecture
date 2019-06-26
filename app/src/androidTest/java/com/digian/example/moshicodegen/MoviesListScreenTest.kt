package com.digian.example.moshicodegen

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digian.example.moshicodegen.ui.MoviesActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Alex Forrester on 2019-04-25.
 */
private const val ITEM_BELOW_THE_FOLD = 18
private const val ITEM_TITLE_ABOVE_THE_FOLD = "The Shawshank Redemption"
private const val ITEM_TITLE_BELOW_THE_FOLD = "12 Angry Men"

@RunWith(AndroidJUnit4::class)
class MoviesListScreenTest {

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MoviesActivity::class.java)
    }

    @Test
    fun given_app_first_loaded_when_viewing_list_then_movie_title_above_fold_displayed() {

        val itemElementText = ITEM_TITLE_ABOVE_THE_FOLD
        onView(withText(itemElementText)).check(matches(isDisplayed()))
    }

    @Test
    fun given_app_first_loaded_when_viewing_list_then_movie_title_below_fold_not_displayed() {

        val itemElementText = ITEM_TITLE_BELOW_THE_FOLD
        onView(withText(itemElementText)).check(doesNotExist())
    }

    @Test
    fun given_app_first_loaded_when_scrolled_to_below_the_fold_then_movie_title_is_displayed() {

        onView(withId(R.id.movies_recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                ITEM_BELOW_THE_FOLD
            )
        )

        val itemElementText = ITEM_TITLE_BELOW_THE_FOLD

        onView(withText(itemElementText)).check(matches(isDisplayed()))

    }

    @Test
    fun given_app_first_loaded_and_scrolled_to_below_the_fold_when_clicked_then_movie_details_are_displayed() {

        onView(withId(R.id.movies_recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                ITEM_BELOW_THE_FOLD
            )
        )

        val movieTitleText = ITEM_TITLE_BELOW_THE_FOLD
        val movieDescriptionText =
            "The defense and the prosecution have rested and the jury is filing into the jury room to decide if a young Spanish-American is guilty or innocent of murdering his father. What begins as an open and shut case soon becomes a mini-drama of each of the jurors' prejudices and preconceptions about the trial, the accused, and each other."
        val movieVotes = "VOTES: 3603"
        val movieGenres = "GENRES: Drama"

        onView(withId(R.id.movies_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_BELOW_THE_FOLD, click())
        )

        onView(withText(movieTitleText)).check(matches(isDisplayed()))
        onView(withText(movieDescriptionText)).check(matches(isDisplayed()))
        onView(withText(movieVotes)).check(matches(isDisplayed()))
        onView(withText(movieGenres)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_image)).check(matches(isDisplayed()))

    }



}