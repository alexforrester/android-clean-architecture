package com.digian.clean.features.movies.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Alex Forrester on 11/04/2019.
 */
@JsonClass(generateAdapter = true)
data class MovieData (
    val id: Int,
    @Json(name = "vote_count") val voteCount: Int = -1,
    val title: String,
    @Json(name = "image_path") val imagePath: String,
    @Json(name = "genre_ids") val genresData: List<GenreData>,
    val overview: String
)


