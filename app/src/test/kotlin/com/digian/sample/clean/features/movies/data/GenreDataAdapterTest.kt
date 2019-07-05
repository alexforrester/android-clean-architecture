package com.digian.sample.clean.features.movies.data

import com.digian.sample.clean.features.movies.data.entities.GenreData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Created by Alex Forrester on 2019-04-27.
 */
internal class GenreDataAdapterTest {

    val genres = listOf(
        GenreData(28, "Action"),
        GenreData(12, "Adventure"),
        GenreData(16, "Animation")
    )
    val moshi = Moshi.Builder().add(GenreAdapter()).build()
    val listType = Types.newParameterizedType(List::class.java, GenreData::class.java)
    val adapter: JsonAdapter<List<GenreData>> = moshi.adapter(listType)

    @Test
    internal fun `given list of genres to serialise to json, when custom adapter used, then array of Ints created`() {

        val genresJson = adapter.toJson(genres)
        assertEquals("""[28,12,16]""", genresJson)
    }

    @Test
    internal fun `given array of genres ids, when custom adapter used, then list of Genres created`() {

        val genresList = adapter.fromJson("[28,12,16]")
        assertEquals(genres, genresList)
    }

}