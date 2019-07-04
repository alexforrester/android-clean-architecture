package com.digian.sample.clean.movies.domain

import com.digian.sample.clean.movies.domain.usecases.entities.MovieEntity

interface PopularMoviesRepository {
    fun getMovies(): List<MovieEntity>
    fun getMovieDetail(): MovieEntity
}
