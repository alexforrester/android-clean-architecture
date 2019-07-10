package com.digian.clean.features.movies.domain.entities

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MovieEntityTest {

    private lateinit var movieEntity : MovieEntity

    @BeforeEach
    fun setUp() {

        movieEntity = MovieEntity(238,
            9714,
            "The Godfather",
            "/rPdtLWNsZmAtoZl9PK7S2wE3qiS.jpg",
            listOf(GenreEntity(18, "Drama"),GenreEntity(80, "Crime")),
            "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.")
    }

    @Test
    fun `given movie entity created, when short overview property accessed, then overview of 150 characters or less is returned`() {

        assertEquals(148, movieEntity.shortOverview.length)
        assertEquals("Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito...",
            movieEntity.shortOverview)
    }
}