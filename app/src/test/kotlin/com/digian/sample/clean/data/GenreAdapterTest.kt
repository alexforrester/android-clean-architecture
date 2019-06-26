package com.digian.sample.clean.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Created by Alex Forrester on 2019-04-27.
 */
internal class GenreAdapterTest {

    val genres = listOf(Genre(28,"Action"),Genre(12, "Adventure"),Genre(16, "Animation"))
    val moshi = Moshi.Builder().add(GenreAdapter()).build()
    val listType = Types.newParameterizedType(List::class.java, Genre::class.java)
    val adapter: JsonAdapter<List<Genre>> = moshi.adapter(listType)

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