package com.digian.sample.clean.features.movies.domain

import com.digian.sample.clean.features.movies.domain.entities.MovieEntity

interface PopularMoviesRepository {
    fun getMovies(): List<MovieEntity>
    fun getMovieDetail(): MovieEntity
}
