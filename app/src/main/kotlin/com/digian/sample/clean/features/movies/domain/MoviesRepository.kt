package com.digian.sample.clean.features.movies.domain

import com.digian.sample.clean.core.domain.UseCaseResult
import com.digian.sample.clean.core.domain.exception.Failure
import com.digian.sample.clean.features.movies.domain.entities.MovieEntity

interface PopularMoviesRepository {
    fun getMovies(): UseCaseResult<Failure, List<MovieEntity>>
}
