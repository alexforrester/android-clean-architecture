package com.digian.clean.features.movies.data.mappers

import com.digian.clean.features.movies.data.GenreData
import com.digian.clean.features.movies.data.MovieData
import com.digian.clean.features.movies.domain.entities.GenreEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Created by Alex Forrester on 2019-07-11.
 */
internal class MovieDataEntityMapperTest {

    private lateinit var movieData : MovieData

    @BeforeEach
    fun setUp() {

        movieData = MovieData(238,
            9714,
            "The Godfather",
            "/rPdtLWNsZmAtoZl9PK7S2wE3qiS.jpg",
            listOf(GenreData(18, "Drama"), GenreData(80, "Crime")),
            "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.")

    }

    @Test
    fun `given movie data, when transformed to movie entity, then correct mapping performed`() {

        val movieEntity = MovieDataEntityMapper.mapFrom(movieData)

        assertEquals(238, movieEntity.id)
        assertEquals(9714, movieEntity.voteCount)
        assertEquals("The Godfather", movieEntity.title)
        assertEquals("/rPdtLWNsZmAtoZl9PK7S2wE3qiS.jpg", movieEntity.imagePath)
        assertEquals(listOf(GenreEntity(18, "Drama"), GenreEntity(80, "Crime")), movieEntity.genres)
        assertEquals("Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
            movieEntity.overview)

    }
}