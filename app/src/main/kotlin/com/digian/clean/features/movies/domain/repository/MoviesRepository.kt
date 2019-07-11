package com.digian.clean.features.movies.domain.repository

import com.digian.clean.features.core.domain.UseCaseResult
import com.digian.clean.features.core.data.exception.Failures
import com.digian.clean.features.core.domain.exception.Failure
import com.digian.clean.features.movies.domain.entities.MovieEntity

interface PopularMoviesRepository {
    fun getMovies(): UseCaseResult<Failure, List<MovieEntity>>
}
