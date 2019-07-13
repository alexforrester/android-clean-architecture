package com.digian.clean.features.movies.domain.entities

/**
 * Created by Alex Forrester on 11/04/2019.
 */
data class MovieEntity(
    val id: Int,
    val voteCount: Int,
    val title: String,
    val imagePath: String,
    val genres: List<GenreEntity>,
    val overview: String
) {
    //Contract Overview to 150 characters max ending with a whole word followed by "..."
    val shortOverview: String
        get() = overview.substring(0, 150).replaceAfterLast(" ", "").trim().plus("...")
}


