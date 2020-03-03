package com.digian.clean.features.movies.data

import com.squareup.moshi.JsonClass

/**
 * Created by Alex Forrester on 2019-04-26.
 */
@JsonClass(generateAdapter = true)
data class GenreData(val id: Int, val name: String)