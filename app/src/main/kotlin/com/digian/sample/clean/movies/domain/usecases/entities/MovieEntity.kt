package com.digian.sample.clean.movies.domain.usecases.entities

/**
 * Created by Alex Forrester on 11/04/2019.
 */
data class MovieEntity (
    val voteCount: Int,
    val id: Int,
    val title: String,
    val imagePath: String,
    val genres: List<GenreEntity>,
    val overview: String
)


