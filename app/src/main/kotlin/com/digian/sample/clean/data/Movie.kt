package com.digian.example.moshicodegen.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Alex Forrester on 11/04/2019.
 */
@JsonClass(generateAdapter = true)
data class Movie (
    @Json(name = "vote_count") val voteCount: Int = -1,
    val id: Int,
    val title: String,
    @Json(name = "image_path") val imagePath: String,
    @Json(name = "genre_ids") val genres: List<Genre>,
    val overview: String
)


