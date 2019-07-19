package com.digian.clean.features.movies.domain.repository

import com.digian.clean.core.domain.exception.Failure
import com.digian.clean.core.domain.ports.UseCaseInput
import com.digian.clean.core.domain.ports.UseCaseOutput
import com.digian.clean.features.movies.domain.entities.MovieEntity

interface MoviesRepository {
    suspend fun getMovies(none: UseCaseInput.None): UseCaseOutput<Failure, List<MovieEntity>>
    suspend fun getMovieDetail(movieIdInput: UseCaseInput.Single<Int>): UseCaseOutput<Failure, MovieEntity>
}
