package com.digian.sample.clean.ui

import com.digian.sample.clean.data.Genre
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Created by Alex Forrester on 2019-04-28.
 */
internal class MovieDetailFragmentCompanionTest {

    private val genres = listOf(Genre(28,"Action"), Genre(12, "Adventure"), Genre(16, "Animation"))
    private val emptyGenres = emptyList<Genre>()


    @Test
    internal fun `given list of genres, when prepared for display print genre strings prepended with GENRES text`() {

        val genreNames = MovieDetailFragment.createGenreText(genres)

        assertEquals("GENRES: Action, Adventure, Animation", genreNames)
    }

    @Test
    internal fun `given empty list of genres, when prepared for display blank string returned`() {

        val genreNames = MovieDetailFragment.createGenreText(emptyGenres)

        assertEquals("", genreNames)
    }
}