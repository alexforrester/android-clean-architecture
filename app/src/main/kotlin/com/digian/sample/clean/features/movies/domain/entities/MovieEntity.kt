package com.digian.sample.clean.features.movies.domain.entities

/**
 * Created by Alex Forrester on 11/04/2019.
 */
data class MovieEntity (
    val id: Int,
    val voteCount: Int,
    val title: String,
    val imagePath: String,
    val genres: List<GenreEntity>,
    val overview: String
)


