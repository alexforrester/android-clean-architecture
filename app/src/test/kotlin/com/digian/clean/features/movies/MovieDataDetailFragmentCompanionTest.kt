package com.digian.clean.features.movies

import com.digian.clean.features.movies.domain.entities.GenreEntity
import com.digian.clean.features.movies.presentation.MovieDetailFragment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Created by Alex Forrester on 2019-04-28.
 */
internal class MovieDataDetailFragmentCompanionTest {

    private val genres = listOf(
        GenreEntity(28, "Action"),
        GenreEntity(12, "Adventure"),
        GenreEntity(16, "Animation")
    )

    private val emptyGenres = emptyList<GenreEntity>()

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